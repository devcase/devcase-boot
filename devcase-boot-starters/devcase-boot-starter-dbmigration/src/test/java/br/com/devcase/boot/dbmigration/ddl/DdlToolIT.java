package br.com.devcase.boot.dbmigration.ddl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.dbmigration.ddl.DdlTool;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes=IntegrationTestConfiguration.class)
@ActiveProfiles("integration-test")
public class DdlToolIT {
	
	@Autowired
	DdlTool hibernateSchemaTool;	

	@Test
	public void test() throws Exception {
		Assert.assertNotNull(hibernateSchemaTool);
		String updateScript = hibernateSchemaTool.generateUpdateScript();
		Assert.assertTrue(updateScript.contains("create table example_entity"));
	}
}
