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
import org.eclipse.epsilon.eol.types.EolSequence;

import it.gssi.cs.modeling.digitaltwin.launcher.EplEvlStandaloneExample;

public class DigitalShadowInjector implements Runnable{

	public static void main(String[] args) {
		
		 DigitalShadowInjector ds = new DigitalShadowInjector();
		  
		    float value =  (float) Double.parseDouble("2.0");
		    ds.updateDigitalShadow("models/smartbuilding/gssi.model", "models/smartbuilding/smartBuildingDL.ecore","Office 1", "temperature",value);
		
		
	}
	
	public EolSequence getRequiredDigitalShadow(String subjectmodel,String subjectMM, String subjectname) throws URISyntaxException {
		StringProperties modelProperties = new StringProperties();
		
		Path root = Paths.get(DigitalShadowInjector.class.getResource("").toURI()),
				qesRoot = root.getParent().resolve("qes"),
				mmRoot = root.getParent().resolve("metamodels")
				;
		
		EmfModel targetModel = new EmfModel();
		
				
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "subjectmodel");
		targetProperties.setProperty(EmfModel.PROPERTY_ALIASES, "subjectmodel");
		targetProperties.setProperty(EmfModel.PROPERTY_EXPAND, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, subjectMM);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
				URI.createFileURI(new File(subjectmodel).getAbsolutePath()).toString()
		);
		
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "false");
		
	EolRunConfiguration runConfig = EolRunConfiguration.Builder()
			.withScript(qesRoot.resolve("getDigitalShadow.eol"))
			.withModel(targetModel,targetProperties)
			.withParameter("subjecttype", "Room")
			.withParameter("subjectname", subjectname)
			//.withProfiling()
			.build();
	
		//System.out.println(runConfig.getResult());
	runConfig.run();	
	
		
		
		//System.out.println(runConfig.getResult());
		
		
	
	EolSequence results = (EolSequence) runConfig.getResult();
	//System.out.println("Required parameters in the loaded smart city model: "+results.toString());
	return results;
		
	}

	public boolean updateDigitalShadow(String subjectmodel,String subjectMM, String subjectname, String param, float value) {
		StringProperties modelProperties = new StringProperties();
		boolean result = false;
		Path root, qesRoot, mmRoot;
		try {
			root = Paths.get(DigitalShadowInjector.class.getResource("").toURI());
			qesRoot = root.getParent().resolve("qes");
			mmRoot = root.getParent().resolve("metamodels");
		
		
		EmfModel targetModel = new EmfModel();
		
				
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "subjectmodel");
		targetProperties.setProperty(EmfModel.PROPERTY_ALIASES, "subjectmodel");
		targetProperties.setProperty(EmfModel.PROPERTY_EXPAND, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_FILE_BASED_METAMODEL_URI, subjectMM);
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
				URI.createFileURI(new File(subjectmodel).getAbsolutePath()).toString()
		);
		
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "true");
		
	EolRunConfiguration runConfig = EolRunConfiguration.Builder()
			.withScript(qesRoot.resolve("updateDigitalShadow.eol"))
			.withModel(targetModel,targetProperties)
			.withParameter("subjecttype", "Room")
			.withParameter("subjectname", subjectname)
			.withParameter("param", param)
			.withParameter("value", value)
			//.withProfiling()
			.build();
	
		//System.out.println(runConfig.getResult());
	runConfig.run();	
	result = (boolean) runConfig.getResult();
	
	targetModel.dispose();
	
	
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	
	}
		return result;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
