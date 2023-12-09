# DTModelingTools
This online repository provides additional material accompanying the paper "Model-based Quality Assessment for Digital Twins which is currently under review for submission at the International Conference on Advanced Information Systems Engineering 2024.

# Prototypical implementation
In the folder [./src/it/gssi/cs/modeling/digitaltwin](src/it/gssi/cs/modeling/digitaltwin) we provide prototypical implementations of the following components of the proposed architecture in this online repository:
- **Modeling Languages** : The ecore implementation of the proposed language for modeling domain descriptions of DTs (i.e., the DTML) is available in the folder [./src/it/gssi/cs/modeling/digitaltwin/metamodels](src/it/gssi/cs/modeling/digitaltwin/metamodels) as file DTMM.emf, and the language for defining KPIs is available in the same folder as file kpi.emf
- **DT2Ecore transformation**: The ETL transformation that transforms DTML models into ecore meta-models is available in the folder[./src/it/gssi/cs/modeling/digitaltwin/transformations](src/it/gssi/cs/modeling/digitaltwin/transformations)
- **DSInjector implementation**: The available implementations of the proposed DS injector are available in the folder [./src/it/gssi/cs/modeling/digitaltwin/shadow](src/it/gssi/cs/modeling/digitaltwin/shadow)
- **QES engine**: The full EOL scripts required to transform DT models conforming to a DT domain definition language and KPI models into evaluated DT Models are available in the folder [./src/it/gssi/cs/modeling/digitaltwin/qes](src/it/gssi/cs/modeling/digitaltwin/qes).


## Demonstration Cases
The following artefacts are available as additional material for the presented demonstration cases:
- **Demonstration Case 1: Smart Manufacturing**: The folder [./models/industry4.0](./models/industry4.0) contains an example DTDD model (industry40.model), the resulting DTDD modeling language after applying the DT2Ecore transformation (industry4.0.ecore), the DT model of an example product line (productionLine.model), as well as a KPI model containing the KPIs for this demonstration case described in the paper.
- **Demonstration Case 2 (Air Quality) and Demonstration Case 3 (Energy Consumption)**: The folder [./models/smartbuilding](./models/smartbuilding) contains an example DTDD model (smartbulding.model), the resulting DTDD modeling language after applying the DT2Ecore transformation (smartbuildingDL.ecore), and the example DT model of the GSSI building (gssi.ecore). The KPI model of the air quality case are available in the sub-folder [./models/smartbuilding/airquality](./models/smartbuilding/airquality), and the KPI model of the energy consumption case is available in the sub-folder [./models/smartbuilding/energyconsumption](./models/smartbuilding/energycomsumption).

We aso provide a video demonstrating the whole proposed pipeline for the air quality case evaluated on the GSSI building on youtube:
[![Embeddeed Youtube Video. Original Link: https://www.youtube.com/watch?v=vkTL1EfC3Go](https://www.youtube.com/watch?v=vkTL1EfC3Go)](https://www.youtube.com/watch?v=vkTL1EfC3Go)
