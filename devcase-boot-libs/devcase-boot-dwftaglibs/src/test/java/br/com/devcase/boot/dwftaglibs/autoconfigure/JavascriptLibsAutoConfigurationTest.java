package br.com.devcase.boot.dwftaglibs.autoconfigure;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.devcase.boot.dwftaglibs.autoconfigure.JavascriptLibsAutoConfiguration.JavascriptLib;

@RunWith(SpringRunner.class)
@SpringBootTest()
@ContextConfiguration(classes = JavascriptLibsAutoConfiguration.class)
@ActiveProfiles({ "test" })
public class JavascriptLibsAutoConfigurationTest {

	@Autowired
	JavascriptLibsAutoConfiguration javascriptLibsAutoConfiguration;
	
	@Test
	public void test() throws Exception {
		Map<String, JavascriptLib> libsMap = javascriptLibsAutoConfiguration.getJavascriptLibsMap();
		for (Map.Entry<String, JavascriptLib> lib : libsMap.entrySet()) {
			System.out.println(lib.getValue().getOrder() + " " + lib.getValue().getName());
		}
		Assert.assertTrue(libsMap.containsKey("jquery"));
		Assert.assertTrue(libsMap.containsKey("bootstrap"));
	}
}
