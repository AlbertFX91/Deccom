package com.deccom.config.dbmigrations;

import java.time.Instant;
import java.time.LocalDate;
import java.util.stream.IntStream;

import org.springframework.data.mongodb.core.MongoTemplate;

import com.deccom.domain.Acme;
import com.deccom.domain.Authority;
import com.deccom.domain.SQLConnection;
import com.deccom.domain.SQLControlVar;
import com.deccom.domain.SQLControlVarEntry;
import com.deccom.domain.User;
import com.deccom.security.AuthoritiesConstants;
import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.google.common.collect.Lists;

/**
 * Creates the initial database setup
 */
@ChangeLog(order = "001")
public class InitialSetupMigration {

    @ChangeSet(order = "01", author = "initiator", id = "01-addAuthorities")
    public void addAuthorities(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);
        mongoTemplate.save(adminAuthority);
        mongoTemplate.save(userAuthority);
    }

    @ChangeSet(order = "02", author = "initiator", id = "02-addUsers")
    public void addUsers(MongoTemplate mongoTemplate) {
        Authority adminAuthority = new Authority();
        adminAuthority.setName(AuthoritiesConstants.ADMIN);
        Authority userAuthority = new Authority();
        userAuthority.setName(AuthoritiesConstants.USER);

        User systemUser = new User();
        systemUser.setId("user-0");
        systemUser.setLogin("system");
        systemUser.setPassword("$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG");
        systemUser.setFirstName("");
        systemUser.setLastName("System");
        systemUser.setEmail("system@localhost");
        systemUser.setActivated(true);
        systemUser.setLangKey("en");
        systemUser.setCreatedBy(systemUser.getLogin());
        systemUser.setCreatedDate(Instant.now());
        systemUser.getAuthorities().add(adminAuthority);
        systemUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(systemUser);

        User anonymousUser = new User();
        anonymousUser.setId("user-1");
        anonymousUser.setLogin("anonymoususer");
        anonymousUser.setPassword("$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO");
        anonymousUser.setFirstName("Anonymous");
        anonymousUser.setLastName("User");
        anonymousUser.setEmail("anonymous@localhost");
        anonymousUser.setActivated(true);
        anonymousUser.setLangKey("en");
        anonymousUser.setCreatedBy(systemUser.getLogin());
        anonymousUser.setCreatedDate(Instant.now());
        mongoTemplate.save(anonymousUser);

        User adminUser = new User();
        adminUser.setId("user-2");
        adminUser.setLogin("admin");
        adminUser.setPassword("$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC");
        adminUser.setFirstName("admin");
        adminUser.setLastName("Administrator");
        adminUser.setEmail("admin@localhost");
        adminUser.setActivated(true);
        adminUser.setLangKey("en");
        adminUser.setCreatedBy(systemUser.getLogin());
        adminUser.setCreatedDate(Instant.now());
        adminUser.getAuthorities().add(adminAuthority);
        adminUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(adminUser);

        User userUser = new User();
        userUser.setId("user-3");
        userUser.setLogin("user");
        userUser.setPassword("$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K");
        userUser.setFirstName("");
        userUser.setLastName("User");
        userUser.setEmail("user@localhost");
        userUser.setActivated(true);
        userUser.setLangKey("en");
        userUser.setCreatedBy(systemUser.getLogin());
        userUser.setCreatedDate(Instant.now());
        userUser.getAuthorities().add(userAuthority);
        mongoTemplate.save(userUser);
    }
    @ChangeSet(order = "03", author = "initiator", id = "03-addAcmes")
    public void addAcmes(MongoTemplate mongoTemplate) {
    	Integer numAcmes = 40;
        IntStream.range(0, numAcmes).forEach(
        		index -> {
        	    	Acme acme = new Acme();
        	    	acme.setId("acme-"+index);
        	    	acme.setTitle("Title acme - "+index);
        	    	acme.setDescription("Description acme - "+index);
        	    	acme.setPublication_date(LocalDate.now());
        	    	acme.setRating(index % 10);
        	        mongoTemplate.save(acme);
        		});

    }
    
    @ChangeSet(order = "04", author = "initiator", id = "04-addSQLControlVar")
    public void addSQLControlVar(MongoTemplate mongoTemplate) {
    	SQLControlVar cv = new SQLControlVar();
        cv.setId("SQLControlVar-1");
        cv.setCreationMoment(LocalDate.now());
        cv.setName("controlvar-1");
        cv.setQuery("select age  from author where  idauthor=1 and name=name-1");
        cv.setSqlConnection(new SQLConnection("developer", "developer", "jdbc:mysql://localhost:3306/deccom"));
        cv.setSqlControlVarEntries(Lists.newArrayList(
        		new SQLControlVarEntry("16", LocalDate.now()),
        	    new SQLControlVarEntry("17", LocalDate.now()),
        	    new SQLControlVarEntry("18", LocalDate.now())
        ));
        mongoTemplate.save(cv);
    }

}
