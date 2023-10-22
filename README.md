# Synthea Highmed
Generates Synthetic FHIR Resources based on the modules defined by the [Medical Informatics Initiate](https://www.medizininformatik-initiative.de/en/basic-modules-mii-core-data-set)

## Minimum System Requirements:
- 8GB RAM
- 2vCPU

## Usage
Running using [Docker](https://docs.docker.com/get-docker/):

```
docker run -p 3000:3000 ghcr.io/medblocks/synthea_highmed:main
```

- Visit http://localhost:3000
- Select appropriate Demographics settings
  - Select a population size. 500 patients takes about 10 - 15 mins to generate, and this scales linearly.
  - Seed can be used to reproduce the same results during another run.
  - Play around with other options, or leave them at the default.
- Select modules to generate example data for.
  - Alive only will output a bundle where all patients are alive
  - Fhir Fuzz will output invalid FHIR resources
  - Semantically Invalid modeuls will output FHIR resources that don't make clinical sense. Eg: Male Pregnancies, Females with prostate cancer etc.
  - The Diagnosis, Medication and Lab modules depend on the Patient and Encounter modules.
- Click on "Generate Data"! You will receive a zip in your downloads after synthesis is done.
- Extract the zip, and go to the `fhir` folder to view the FHIR resources generated for your population.

A hosted version of this is temporarily available at [https://highmed.dev.medblocks.com](https://highmed.dev.medblocks.com/)
