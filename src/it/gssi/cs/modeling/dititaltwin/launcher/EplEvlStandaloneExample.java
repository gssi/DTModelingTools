package it.gssi.cs.modeling.dititaltwin.launcher;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;

import org.eclipse.epsilon.common.util.StringProperties;
import org.eclipse.epsilon.emc.emf.EmfModel;
import org.eclipse.epsilon.emc.emf.EmfUtil;
import org.eclipse.epsilon.eol.exceptions.models.EolModelLoadingException;
import org.eclipse.epsilon.eol.models.IModel;
import org.eclipse.epsilon.epl.EplModule;
import org.eclipse.epsilon.epl.execute.PatternMatch;
import org.eclipse.epsilon.epl.execute.model.PatternMatchModel;
import org.eclipse.epsilon.epl.launch.EplRunConfiguration;
import org.eclipse.epsilon.etl.launch.EtlRunConfiguration;



public class EplEvlStandaloneExample {
	
	public static void main(String[] args) throws Exception {
	
		Path root = Paths.get(EplEvlStandaloneExample.class.getResource("").toURI()),
				qesRoot = root.getParent().resolve("qes"),
				mmRoot = root.getParent().resolve("metamodels")
				;
		String subjectfile = "models/smartbuilding/gssi.model";
		
		System.out.println("Subject model: "+subjectfile);
		
		org.eclipse.emf.common.util.URI smartbuildingmm = org.eclipse.emf.common.util.URI.createURI(new File("models/smartbuilding/smartBuildingDL.ecore").getAbsolutePath());
		org.eclipse.emf.common.util.URI kpimm = org.eclipse.emf.common.util.URI.createURI( mmRoot.resolve("kpi.ecore").toAbsolutePath().toUri().toString());
		org.eclipse.emf.common.util.URI DTLmm = org.eclipse.emf.common.util.URI.createURI( mmRoot.resolve("DTMM.ecore").toAbsolutePath().toUri().toString());
		
		
		EmfUtil.register(smartbuildingmm, EPackage.Registry.INSTANCE);
		EmfUtil.register(kpimm, EPackage.Registry.INSTANCE);
		EmfUtil.register(DTLmm, EPackage.Registry.INSTANCE);
		/*StringProperties sourceProperties = new StringProperties();
		sourceProperties.setProperty(EmfModel.PROPERTY_NAME, "SourceWeaving");
		sourceProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		sourceProperties.setProperty(EmfModel.PROPERTY_METAMODEL_URI,"http://cs.gssi.it/digitaltwin/smartbuilding-kpi");
		sourceProperties.setProperty(EmfModel.PROPERTY_MODEL_URI, org.eclipse.emf.common.util.URI.createFileURI(new File("models/smartbuilding/instance-subject-kpi.model").getAbsolutePath()).toString());
		sourceProperties.setProperty(EmfModel.PROPERTY_EXPAND, "true");
		EmfModel sourceweaving = new EmfModel();
		*/

		StringProperties DTLmodelprop = new StringProperties();
		DTLmodelprop.setProperty(EmfModel.PROPERTY_NAME, "DTLmodel");
		DTLmodelprop.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		DTLmodelprop.setProperty(EmfModel.PROPERTY_METAMODEL_URI,"http://cs.gssi.it/digitaltwinDL");
		DTLmodelprop.setProperty(EmfModel.PROPERTY_MODEL_URI,  org.eclipse.emf.common.util.URI.createFileURI(new File("models/smartbuilding/smartbuilding.model").getAbsolutePath()).toString());
		EmfModel DTLmodel = new EmfModel();
		
		StringProperties subjectmodelprop = new StringProperties();
		subjectmodelprop.setProperty(EmfModel.PROPERTY_NAME, "subjectmodel");
		subjectmodelprop.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		subjectmodelprop.setProperty(EmfModel.PROPERTY_METAMODEL_URI,"SmartbuildingDT");
		File subjectf = new File(subjectfile);
		
		subjectmodelprop.setProperty(EmfModel.PROPERTY_MODEL_URI,  org.eclipse.emf.common.util.URI.createFileURI(subjectf.getAbsolutePath()).toString());
		EmfModel subjectmodel = new EmfModel();
		subjectmodel.load(subjectmodelprop);
		
		
		StringProperties kpimodelprop = new StringProperties();
		kpimodelprop.setProperty(EmfModel.PROPERTY_MODEL_URI, org.eclipse.emf.common.util.URI.createFileURI(new File("models/smartbuilding/mykpi.flexmi.model").getAbsolutePath()).toString());
		kpimodelprop.setProperty(EmfModel.PROPERTY_METAMODEL_URI,"http://cs.gssi.it/kpi");
		kpimodelprop.setProperty(EmfModel.PROPERTY_READONLOAD, "true");
		kpimodelprop.setProperty(EmfModel.PROPERTY_NAME, "kpimodel");
		EmfModel kpimodel = new EmfModel();
		
		// Find parent-child pairs using EPL
		EplRunConfiguration eplruntime = EplRunConfiguration.Builder()
				.withScript(qesRoot.resolve("patterns.epl"))
				.withModel(kpimodel, kpimodelprop)
				.withModel(subjectmodel, subjectmodelprop)
				.withModel(DTLmodel, DTLmodelprop).build();
		eplruntime.run();
		
		PatternMatchModel patternMatchModel = eplruntime.getResult();
		patternMatchModel.setName("patternModel");
		//System.out.println(patternMatchModel.getMatches().toString());
		System.out.println("======================================");
		StringProperties targetProperties = new StringProperties();
		targetProperties.setProperty(EmfModel.PROPERTY_NAME, "TargetKpi");
		targetProperties.setProperty(EmfModel.PROPERTY_METAMODEL_URI, "http://cs.gssi.it/kpi");
		targetProperties.setProperty(EmfModel.PROPERTY_MODEL_URI,
			"models/smartbuilding/evaluated-"+subjectf.getName()
		);
		targetProperties.setProperty(EmfModel.PROPERTY_READONLOAD, "false");
		targetProperties.setProperty(EmfModel.PROPERTY_STOREONDISPOSAL, "true");
		
	
		EtlRunConfiguration runConfig = EtlRunConfiguration.Builder()
			.withScript(qesRoot.resolve("kpi2eval.etl"))
			.withModel( patternMatchModel)
			.withModel(kpimodel, kpimodelprop)
			.withModel(subjectmodel, subjectmodelprop)
			.withModel(DTLmodel, DTLmodelprop)
			.withModel(new EmfModel(), targetProperties)
			.build();
		
		runConfig.run();
		runConfig.dispose();
	
	}
	
}
