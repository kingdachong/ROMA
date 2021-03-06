package edu.mit.cci.roma.server;

import edu.mit.cci.roma.impl.DefaultSimulation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@Transactional
public class DefaultSimulationIntegrationTest {

    @Test
    public void testMarkerMethod() {
    }



    @Autowired
    private DefaultSimulationDataOnDemand dod;

    @Test
    public void testCountDefaultSimulations() {
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", dod.getRandomDefaultSimulation());
        long count = DefaultServerSimulation.countDefaultServerSimulations();
        org.junit.Assert.assertTrue("Counter for 'DefaultSimulation' incorrectly reported there were no entries", count > 0);
    }

    @Test
    public void testFindDefaultSimulation() {
        DefaultSimulation obj = dod.getRandomDefaultSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to provide an identifier", id);
        obj = DefaultServerSimulation.findDefaultServerSimulation(id);
        org.junit.Assert.assertNotNull("Find method for 'DefaultSimulation' illegally returned null for id '" + id + "'", obj);
        org.junit.Assert.assertEquals("Find method for 'DefaultSimulation' returned the incorrect identifier", id, obj.getId());
    }

    @Test
    public void testFindAllDefaultSimulations() {
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", dod.getRandomDefaultSimulation());
        long count = DefaultServerSimulation.countDefaultServerSimulations();
        org.junit.Assert.assertTrue("Too expensive to perform a find all test for 'DefaultSimulation', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        java.util.List<DefaultServerSimulation> result = DefaultServerSimulation.findAllDefaultServerSimulations();
        org.junit.Assert.assertNotNull("Find all method for 'DefaultSimulation' illegally returned null", result);
        org.junit.Assert.assertTrue("Find all method for 'DefaultSimulation' failed to return any data", result.size() > 0);
    }

    @Test
    public void testFindDefaultSimulationEntries() {
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", dod.getRandomDefaultSimulation());
        long count = DefaultServerSimulation.countDefaultServerSimulations();
        if (count > 20) count = 20;
        java.util.List<DefaultServerSimulation> result = DefaultServerSimulation.findDefaultServerSimulationEntries(0, (int) count);
        org.junit.Assert.assertNotNull("Find entries method for 'DefaultSimulation' illegally returned null", result);
        org.junit.Assert.assertEquals("Find entries method for 'DefaultSimulation' returned an incorrect number of entries", count, result.size());
    }

    @Test
    public void testFlush() {
        DefaultServerSimulation obj = dod.getRandomDefaultSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to provide an identifier", id);
        obj = DefaultServerSimulation.findDefaultServerSimulation(id);
        org.junit.Assert.assertNotNull("Find method for 'DefaultSimulation' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyDefaultSimulation(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        obj.flush();
        org.junit.Assert.assertTrue("Version for 'DefaultSimulation' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testMerge() {
        DefaultServerSimulation obj = dod.getRandomDefaultSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to provide an identifier", id);
        obj = DefaultServerSimulation.findDefaultServerSimulation(id);
        boolean modified =  dod.modifyDefaultSimulation(obj);
        java.lang.Integer currentVersion = obj.getVersion();
        DefaultSimulation merged = (DefaultSimulation) obj.merge();
        obj.flush();
        org.junit.Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        org.junit.Assert.assertTrue("Version for 'DefaultSimulation' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

    @Test
    public void testPersist() {
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", dod.getRandomDefaultSimulation());
        DefaultServerSimulation obj = dod.getNewTransientDefaultSimulation(Integer.MAX_VALUE);
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to provide a new transient entity", obj);
        org.junit.Assert.assertNull("Expected 'DefaultSimulation' identifier to be null", obj.getId());
        obj.persist();
        obj.flush();
        org.junit.Assert.assertNotNull("Expected 'DefaultSimulation' identifier to no longer be null", obj.getId());
    }

    @Test
    public void testRemove() {
        DefaultServerSimulation obj = dod.getRandomDefaultSimulation();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to initialize correctly", obj);
        java.lang.Long id = obj.getId();
        org.junit.Assert.assertNotNull("Data on demand for 'DefaultSimulation' failed to provide an identifier", id);
        obj = DefaultServerSimulation.findDefaultServerSimulation(id);
        obj.remove();
        obj.flush();
        org.junit.Assert.assertNull("Failed to remove 'DefaultSimulation' with identifier '" + id + "'", DefaultServerSimulation.findDefaultServerSimulation(id));
    }
}
