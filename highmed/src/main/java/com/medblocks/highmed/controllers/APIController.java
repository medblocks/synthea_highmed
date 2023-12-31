package com.medblocks.highmed.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import org.mitre.synthea.engine.Generator;
import org.mitre.synthea.helpers.Config;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import java.text.SimpleDateFormat;
import org.apache.commons.io.FileUtils;




@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Content-Disposition")
@RequestMapping("/rest/v1")
public class APIController {
  @Autowired
  SpringWebSocketLogger springWebSocketLogger;

  @PostMapping(value = "generate", produces = "application/zip")
  public ResponseEntity<StreamingResponseBody> zipFolder(@RequestParam Map<String,String> allRequestParams) {
    String outputFolderName = "./output/";


    // create a randomised output folder name
    outputFolderName = "./" + new SimpleDateFormat("yyyy_MM_dd_hhmmss'_synthetic_data'").format(new Date()) + "/";
    Config.set("exporter.baseDirectory", outputFolderName);


    // // run the generator
    Generator.GeneratorOptions options = new Generator.GeneratorOptions();


    // // Checking For USERID
    String userID = "";
    if(allRequestParams.containsKey("userID")){
      userID= allRequestParams.get("userID"); 
    }
    else{
      return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    if(allRequestParams.containsKey("populationSize")){
      int populationSize=Integer.parseInt(allRequestParams.get("populationSize")); 
      options.population = populationSize; 
    }
    if(allRequestParams.containsKey("seed")){
      long seed=Long.parseLong(allRequestParams.get("seed")); 
      options.seed = seed; 
    }
    if(allRequestParams.containsKey("minAge")){
      options.ageSpecified = true;
      int minAge=Integer.parseInt(allRequestParams.get("minAge")); 
      options.minAge = minAge; 
    }
    if(allRequestParams.containsKey("maxAge")){
      options.ageSpecified = true;
      int maxAge=Integer.parseInt(allRequestParams.get("maxAge")); 
      options.maxAge = maxAge; 
    }
    if(allRequestParams.containsKey("gender")){
      String gender=allRequestParams.get("gender"); 
      options.gender = gender; 
    }

    if(allRequestParams.containsKey("onlyAlive")){
      String onlyAlive= allRequestParams.get("onlyAlive"); 
      Config.set("generate.only_alive_patients", onlyAlive);
    }
    
    if(allRequestParams.containsKey("middleNameProbability")){
      String middleNameProbability= allRequestParams.get("middleNameProbability"); 
      Config.set("generate.middle_names", middleNameProbability);
    }

    if(allRequestParams.containsKey("default_procedure_cost")){
      String default_procedure_cost= allRequestParams.get("default_procedure_cost"); 
      Config.set("generate.costs.default_procedure_cost", default_procedure_cost);
    }
    if(allRequestParams.containsKey("default_medication_cost")){
      String default_medication_cost= allRequestParams.get("default_medication_cost"); 
      Config.set("generate.costs.default_medication_cost", default_medication_cost);
    }
    if(allRequestParams.containsKey("default_encounter_cost")){
      String default_encounter_cost= allRequestParams.get("default_encounter_cost"); 
      Config.set("generate.costs.default_encounter_cost", default_encounter_cost);
    }
    if(allRequestParams.containsKey("default_immunization_cost")){
      String default_immunization_cost= allRequestParams.get("default_immunization_cost"); 
      Config.set("generate.costs.default_immunization_cost", default_immunization_cost);
    }
    if(allRequestParams.containsKey("default_lab_cost")){
      String default_lab_cost= allRequestParams.get("default_lab_cost"); 
      Config.set("generate.costs.default_lab_cost", default_lab_cost);
    }
    if(allRequestParams.containsKey("default_device_cost")){
      String default_device_cost= allRequestParams.get("default_device_cost"); 
      Config.set("generate.costs.default_device_cost", default_device_cost);
    }
    if(allRequestParams.containsKey("default_supply_cost")){
      String default_supply_cost= allRequestParams.get("default_supply_cost"); 
      Config.set("generate.costs.default_supply_cost", default_supply_cost);
    }

    
    if(allRequestParams.containsKey("fuzzFhir")){
      String fuzzFhir= allRequestParams.get("fuzzFhir"); 
      Config.set("exporter.fhir.fuzz", fuzzFhir);
    }
    else{
      Config.set("exporter.fhir.fuzz", "false");
    }

    Config.set("exporter.fhir.semanticallyInvalidModules", "false");
    if(allRequestParams.containsKey("semanticallyInvalidModules")){
      String semanticallyInvalidModules= allRequestParams.get("semanticallyInvalidModules"); 
      Config.set("exporter.fhir.semanticallyInvalidModules", semanticallyInvalidModules);
    }

    Config.set("exporter.fhir.includePersonModule", "true");
    if(allRequestParams.containsKey("includePersonModule")){
      String includePersonModule= allRequestParams.get("includePersonModule"); 
      Config.set("exporter.fhir.export.person", includePersonModule);
    }


    Config.set("exporter.fhir.includeEncounterModule", "true");
    if(allRequestParams.containsKey("includeEncounterModule")){
      String includeEncounterModule= allRequestParams.get("includeEncounterModule"); 
      Config.set("exporter.fhir.export.case", includeEncounterModule);
    }


    Config.set("exporter.fhir.includeDiagnosisModule", "true");
    if(allRequestParams.containsKey("includeDiagnosisModule")){
      String includeDiagnosisModule= allRequestParams.get("includeDiagnosisModule"); 
      Config.set("exporter.fhir.export.diagnosis", includeDiagnosisModule);
    }

    Config.set("exporter.fhir.includeLabsModule", "true");
    if(allRequestParams.containsKey("includeLabsModule")){
      String includeLabsModule= allRequestParams.get("includeLabsModule"); 
      Config.set("exporter.fhir.export.labs", includeLabsModule);
    }

    Config.set("exporter.fhir.includeMedicationModule", "true");
    if(allRequestParams.containsKey("includeMedicationModule")){
      String includeMedicationModule= allRequestParams.get("includeMedicationModule"); 
      Config.set("exporter.fhir.export.medication", includeMedicationModule);
    }

    if(allRequestParams.containsKey("exportFhir")){
      String exportFhir= allRequestParams.get("exportFhir"); 
      Config.set("exporter.fhir.export", exportFhir);
    }
    if(allRequestParams.containsKey("exportMetaData")){
      String exportMetaData= allRequestParams.get("exportMetaData"); 
      Config.set("exporter.metadata.export", exportMetaData);
    }
    if(allRequestParams.containsKey("exportFhirTransactionBundle")){
      String exportFhirTransactionBundle= allRequestParams.get("exportFhirTransactionBundle"); 
      Config.set("exporter.fhir.transaction_bundle", exportFhirTransactionBundle);
    }
    if(allRequestParams.containsKey("exportFhirHospital")){
      String exportFhirHospital= allRequestParams.get("exportFhirHospital"); 
      Config.set("exporter.hospital.fhir.export", exportFhirHospital);
    }
    if(allRequestParams.containsKey("exportFhirPractitioner")){
      String exportFhirPractitioner= allRequestParams.get("exportFhirPractitioner"); 
      Config.set("exporter.practitioner.fhir.export", exportFhirPractitioner);
    }

    Generator generator = new Generator(options, springWebSocketLogger, userID);
    generator.run();

    // creating a zip of the output folder in-memory and returning it
    File outputFolderLoc = new File(outputFolderName);
    String zipFileName = new SimpleDateFormat("yyyy_MM_dd_hhmmss'_synthetic_data.zip'").format(new Date()); 
    if (outputFolderLoc.exists() && outputFolderLoc.isDirectory()) {
      return ResponseEntity
        .ok()
        .header("Content-Disposition", "attachment; filename=\""+zipFileName+"\"")
        .body(out -> {
          try (ZipOutputStream zipOutputStream = new ZipOutputStream(out)) {
            zipFolderRecursive(outputFolderLoc, outputFolderLoc.getName() + "/", zipOutputStream);
          }
        });
    }
    else{
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }


  public void zipFolderRecursive(File folder, String folderPathInZip, ZipOutputStream zipOutputStream)
      throws IOException {
    if (folder.isDirectory()) {
      File[] files = folder.listFiles();
      if (files != null) {
        for (File file : files) {
          if (file.isDirectory()) {
            zipFolderRecursive(file, folderPathInZip + file.getName() + "/", zipOutputStream);
          } else {
            try (FileInputStream fileInputStream = new FileInputStream(file)) {
              ZipEntry zipEntry = new ZipEntry(folderPathInZip + file.getName());
              zipOutputStream.putNextEntry(zipEntry);
              StreamUtils.copy(fileInputStream, zipOutputStream);
              zipOutputStream.closeEntry();
            }
          }
        }
      }
      FileUtils.deleteDirectory(folder);
    }
  }


}
