package no.nav.pensjon.dsf.web.resources.person;

import no.nav.pensjon.dsf.domene.Person;
import no.nav.pensjon.dsf.web.exceptions.ResourceNotFound;
import no.nav.pensjon.presys.utils.ebcdic.AnnotationMapper;
import no.nav.pensjon.presys.utils.ebcdic.ScrollableArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.Base64;
import java.util.Map;

@Component
public class PersonRepository {
    private static final Logger LOG = LoggerFactory.getLogger(PersonRepository.class);

    private JdbcTemplate db;

    @Inject
    public PersonRepository(JdbcTemplate db){
        this.db = db;
    }

    Person findPerson(String fnr) {
        MDC.put("bruker", fnr);
        try {
            Map<String, Object> map = db.queryForMap("select data from db_person where fnr=?", fnr);
            return AnnotationMapper.les(new ScrollableArray(map.get("data") instanceof String ? Base64.getDecoder().decode((String) map.get("data")) : (byte[]) map.get("data")), Person.class);
        } catch (EmptyResultDataAccessException e){
            throw new ResourceNotFound(e);
        } catch (RuntimeException rte) {
            LOG.warn("Feil ved henting av person", rte);
            throw rte;
        }  finally {
            MDC.remove("bruker");
        }
    }

    boolean exists(String fnr){
        return db.queryForObject("SELECT COUNT(*) FROM DB_PERSON WHERE fnr = ?", new Object[]{fnr} , Integer.class ) > 0;
    }
}
