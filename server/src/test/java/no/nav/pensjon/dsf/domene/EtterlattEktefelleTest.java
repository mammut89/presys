package no.nav.pensjon.dsf.domene;

import no.nav.pensjon.dsf.domene.status.EtterlattEktefelle;
import org.junit.Test;

/**
 * Created by K150562 on 12.06.2017.
 */
public class EtterlattEktefelleTest extends DomeneTestHelper {

    @Test
    public void validerEtterlattEktefelle() throws NoSuchMethodException {
        validerEnkeltSegment(EtterlattEktefelle.class);
    }
}
