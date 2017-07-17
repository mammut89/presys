package no.nav.pensjon.presys.metrics.integration;

import no.nav.pensjon.presys.metrics.MetricsFactory;
import no.nav.pensjon.presys.metrics.TestUtil;
import no.nav.pensjon.presys.metrics.aspects.Count;
import no.nav.pensjon.presys.metrics.aspects.CountAspect;
import no.nav.pensjon.presys.metrics.aspects.Timed;
import no.nav.pensjon.presys.metrics.aspects.TimerAspect;
import org.junit.Test;

import java.net.ServerSocket;
import java.util.List;

import static no.nav.pensjon.presys.metrics.TestUtil.getSensuClientPort;
import static no.nav.pensjon.presys.metrics.TestUtil.lesUtAlleMeldingerSendtPaSocket;
import static org.junit.Assert.assertEquals;

public class MetricReportTest {

    @Test
    public void aspectOgProxySkalRapportereLikeDataForTimer() throws Exception {
        TestUtil.resetMetrics();
        Thread.sleep(100);

        final TimeMe timerProxy = MetricsFactory.createTimerProxy("TimeMe", new TimeMeImpl(), TimeMe.class);
        final TimeMe timerAspect = TestUtil.lagAspectProxy(new TimeMeImpl(), new TimerAspect());

        new Thread(new Runnable() {
            @Override
            public void run() {
                timerProxy.time();
                timerAspect.time();
            }
        }).start();

        sjekkLiktPaSocketData();
    }

    @Test
    public void aspectOgProxySkalRapportereLikeDataForEvent() throws Exception {
        TestUtil.resetMetrics();
        Thread.sleep(100);

        final EventMe eventProxy = MetricsFactory.createEventProxy("EventMe", new EventMeImpl(), EventMe.class);
        final EventMe eventAspect = TestUtil.lagAspectProxy(new EventMeImpl(), new CountAspect());

        new Thread(new Runnable() {
            @Override
            public void run() {
                eventProxy.event();
                eventAspect.event();
            }
        }).start();

        sjekkLiktPaSocketData();
    }

    private void sjekkLiktPaSocketData() throws Exception {
        ServerSocket serverSocket = new ServerSocket(getSensuClientPort());

        List<String> meldinger = lesUtAlleMeldingerSendtPaSocket(serverSocket);

        assertEquals(2, meldinger.size());
        assertEquals(fjernTimestamps(meldinger.get(0)), fjernTimestamps(meldinger.get(1)));

        serverSocket.close();
    }


    private String fjernTimestamps(String data) {
        return data
                .replaceAll("value=\\d+", "value=<dummy>")
                .replaceAll("\\d{19}", "<timestamp>");
    }

    public interface TimeMe {
        void time();
    }

    public static class TimeMeImpl implements TimeMe {
        @Timed
        @Override
        public void time() {

        }
    }

    public interface EventMe {
        void event();
    }

    public static class EventMeImpl implements EventMe {
        @Count
        @Override
        public void event() {

        }
    }


}