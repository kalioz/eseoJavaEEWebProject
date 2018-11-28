package fr.kalioz.eseo.icwebserver;

import fr.kalioz.eseo.icwebserver.apifetch.GouvCommunesTest;
import fr.kalioz.eseo.icwebserver.models.VilleTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GouvCommunesTest.class,
        VilleTest.class
})
public class TestAll {

}
