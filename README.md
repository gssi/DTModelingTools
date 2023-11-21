# DTModelingTools
This online repository provides additional material accompanying the paper "Model-based Quality Assessment for Digital Twins which is currently under review for submission at the nternational Conference on Advanced Information Systems Engineering 2024.

# Prototypical implementation
In the folder [./src/it/gssi/cs/modeling/digitaltwin](src/it/gssi/cs/modeling/digitaltwin) provide prototypical implementations of the following components of the proposed architecture in this online repository:
- **Modeling Languages** : The ecore implementation of the proposed language for modeling domain descriptions of DTs (i.e., the DTML) is available in the folder [./src/it/gssi/cs/modeling/digitaltwin/metamodels](src/it/gssi/cs/modeling/digitaltwin/metamodels) as file DTMM.emf, and the language for defining KPIs is available in the same folder as file kpi.emf
- **DT2Ecore transformation**: The ETL transformation that transforms DTML models into ecore meta-models is available in the folder[./src/it/gssi/cs/modeling/digitaltwin/transformations](src/it/gssi/cs/modeling/digitaltwin/transformations)
- **DSInjector implementation**: The available implementations of the proposed DS injector are available in the folder [./src/it/gssi/cs/modeling/digitaltwin/shadow](src/it/gssi/cs/modeling/digitaltwin/shadow)
- **QES engine**: The full EOL scripts required to transform DT models conforming to a DT domain definition language and KPI models into evaluated DT Models are available in the folder [./src/it/gssi/cs/modeling/digitaltwin/qes](src/it/gssi/cs/modeling/digitaltwin/qes). @Ludovico: can you explain the individual files a bit?
- **DT Cockpit generator**: @ludovico: can we separate the generic generator from the domain-specific dashboard repository and the actual generated dashboards?

## Evaluation
To evaluate our approach, we apply it to generate dashboards for models in the following two domains:
- **Smart Building**: The example domain definition, DT and KPI models, and the generated cockpits for this domain are available in the folder [./models/smartbuilding](./models/smartbuilding)
- **Industry 4.0**: The example domain definition, DT and KPI models, and the generated cockpits for this domain are available in the folder [./models/industry4.0](./models/industry4.0)