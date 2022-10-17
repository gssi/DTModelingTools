package it.gssi.cs.modeling.digitaltwin.shadow;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.URI;
import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.eol.launch.EolRunConfiguration;
import org.eclipse.epsilon.eol.types.EolMap;

import it.gssi.cs.modeling.digitaltwin.launcher.EplEvlStandaloneExample;

public class DigitalShadowInjector {

	public void getRequiredDigitalShadow(String subjectmodel,String subjectMM, String subjectname) throws URISyntaxException {
		StringProperties modelProperties = new StringProperties();
		
		Path root = Paths.get(DigitalShadowInjector.class.getResource("").toURI()),
				qesRoot = root.getParent().resolve("qes"),
				mmRoot = root.getParent().resolve("metamodels")
				;
		
		EmfModel targetModel = new EmfModel();
		
				
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "smartcitymodel");
		targetProperties.setProperty(EmfModel.PROPERTY_ALIASES, "smartcitymodel");
		targetProperties.setProperty(EmfModel.PROPERTY_EXPAND, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, subjectMM);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
				URI.createFileURI(new File(subjectmodel).getAbsolutePath()).toString()
		);
		
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "true");
		
	EolRunConfiguration runConfig = EolRunConfiguration.Builder()
			.withScript(root.resolve("getDigitalShadow.eol"))
			.withModel(targetModel,targetProperties)
			//.withParameter("source", source)
			//.withProfiling()
			.build();
	
		//System.out.println(runConfig.getResult());
	runConfig.run();	
	
		
		
		//System.out.println(runConfig.getResult());
		
		
	
	EolMap results = (EolMap) runConfig.getResult();
	//System.out.println("Required parameters in the loaded smart city model: "+results.toString());
	System.out.println(results);
		
	}

}
