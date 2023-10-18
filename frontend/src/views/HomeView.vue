<template>
  <div v-if="!loading" class="bg-[#e3e4e8]  min-h-screen ">
    <AlertDialog v-model="showResetDialog" :title="'Reset Fields'" :description="'Are you sure you want to reset the fields?'" :confirmButtonText="'Reset'" :cancelButtonText="'Cancel'" :onConfirm="resetFields"  />
    <div class="fixed inset-y-0 z-10 flex w-72 flex-col">
      <div class="flex grow flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white m-6 rounded-lg px-6">
        <div class="flex h-1 shrink-0 items-center"/>
        <nav class="flex flex-1 flex-col">
          <ul role="list" class="flex flex-1 flex-col gap-y-7">
            <li>
              <ul role="list" class="-mx-2 space-y-1">
                <li v-for="item in navigation" :key="item.name">
                  <div @click="changeNav(item)" :class="[activeNav.name == item.name ? 'bg-blue-600 text-white' : 'text-gray-700 hover:text-blue-600 hover:bg-gray-50', 'cursor-pointer group flex gap-x-3 rounded-md p-2 text-sm leading-6 font-semibold']">
                    <component :is="item.icon" :class="[activeNav.name == item.name ? 'text-white' : 'text-gray-400 group-hover:text-blue-600', 'h-6 w-6 shrink-0']" aria-hidden="true" />
                    {{ item.name }}
                  </div>
                </li>
              </ul>
            </li>
            
            <li class="-mx-6 mt-auto">
              <div class="px-4 py-3 text-sm leading-6 ">
                <button @click="showResetDialog=true" type="submit" class="flex mb-3 w-full justify-center rounded-md bg-gray-100 px-3 py-1.5 text-sm hover:text-white text-red-600 shadow-sm hover:bg-red-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-red-600">Reset</button>
                <button @click="generateData()" type="submit" class="flex w-full justify-center rounded-md bg-blue-600 px-3 py-1.5 text-sm font-semibold leading-6 text-white shadow-sm hover:bg-blue-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-blue-600">Generate Data</button>
              </div>
            </li>
          </ul>
        </nav>
      </div>
    </div>

    <main class="lg:pl-72">
    <div class="fixed w-10/12 inset-y-0 z-10 flex flex-col">
      <div class="flex grow flex-col gap-y-5 overflow-y-auto border-r border-gray-200 bg-white m-6 rounded-lg px-6 py-4">
        
        <div v-if="activeNav.name=='General'" id="General">
          <div class=" mt-6 grid grid-cols-2 gap-3">
            <div class=" border p-3 rounded-md">
              <SwitchGroup as="div" class="flex items-center justify-between">
                <span class="flex flex-grow flex-col">
                  <SwitchLabel  as="span" class="block text-sm font-medium text-gray-700" passive>Export Fhir</SwitchLabel>
                  <SwitchDescription as="span" class="text-sm text-gray-500">Nulla amet tempus sit accumsan. Aliquet turpis sed sit lacinia.</SwitchDescription>
                </span>
                <Switch v-model="exportFhir" :class="[exportFhir ? 'bg-blue-600' : 'bg-gray-200', 'relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-3']">
                  <span aria-hidden="true" :class="[exportFhir ? 'translate-x-5' : 'translate-x-0', 'pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out']" />
                </Switch>
              </SwitchGroup>
            </div>

            <div class=" border p-3 rounded-md">
              <SwitchGroup as="div" class="flex items-center justify-between">
                <span class="flex flex-grow flex-col">
                  <SwitchLabel  as="span" class="block text-sm font-medium text-gray-700" passive>Export Metadata</SwitchLabel>
                  <SwitchDescription as="span" class="text-sm text-gray-500">Nulla amet tempus sit accumsan. Aliquet turpis sed sit lacinia.</SwitchDescription>
                </span>
                <Switch v-model="exportMetaData" :class="[exportMetaData ? 'bg-blue-600' : 'bg-gray-200', 'relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-3']">
                  <span aria-hidden="true" :class="[exportMetaData ? 'translate-x-5' : 'translate-x-0', 'pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out']" />
                </Switch>
              </SwitchGroup>
            </div>

            <div class=" border p-3 rounded-md">
              <SwitchGroup as="div" class="flex items-center justify-between">
                <span class="flex flex-grow flex-col">
                  <SwitchLabel  as="span" class="block text-sm font-medium text-gray-700" passive>Export Fhir Transaction Bundle</SwitchLabel>
                  <SwitchDescription as="span" class="text-sm text-gray-500">Nulla amet tempus sit accumsan. Aliquet turpis sed sit lacinia.</SwitchDescription>
                </span>
                <Switch v-model="exportFhirTransactionBundle" :class="[exportFhirTransactionBundle ? 'bg-blue-600' : 'bg-gray-200', 'relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-3']">
                  <span aria-hidden="true" :class="[exportFhirTransactionBundle ? 'translate-x-5' : 'translate-x-0', 'pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out']" />
                </Switch>
              </SwitchGroup>
            </div>

            <div class=" border p-3 rounded-md">
              <SwitchGroup as="div" class="flex items-center justify-between">
                <span class="flex flex-grow flex-col">
                  <SwitchLabel  as="span" class="block text-sm font-medium text-gray-700" passive>Export Fhir Hospital</SwitchLabel>
                  <SwitchDescription as="span" class="text-sm text-gray-500">Nulla amet tempus sit accumsan. Aliquet turpis sed sit lacinia.</SwitchDescription>
                </span>
                <Switch v-model="exportFhirHospital" :class="[exportFhirHospital ? 'bg-blue-600' : 'bg-gray-200', 'relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-3']">
                  <span aria-hidden="true" :class="[exportFhirHospital ? 'translate-x-5' : 'translate-x-0', 'pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out']" />
                </Switch>
              </SwitchGroup>
            </div>

            <div class=" border p-3 rounded-md">
              <SwitchGroup as="div" class="flex items-center justify-between">
                <span class="flex flex-grow flex-col">
                  <SwitchLabel  as="span" class="block text-sm font-medium text-gray-700" passive>Export Fhir Practitioner</SwitchLabel>
                  <SwitchDescription as="span" class="text-sm text-gray-500">Nulla amet tempus sit accumsan. Aliquet turpis sed sit lacinia.</SwitchDescription>
                </span>
                <Switch v-model="exportFhirPractitioner" :class="[exportFhirPractitioner ? 'bg-blue-600' : 'bg-gray-200', 'relative inline-flex h-6 w-11 flex-shrink-0 cursor-pointer rounded-full border-2 border-transparent transition-colors duration-200 ease-in-out focus:outline-none focus:ring-3']">
                  <span aria-hidden="true" :class="[exportFhirPractitioner ? 'translate-x-5' : 'translate-x-0', 'pointer-events-none inline-block h-5 w-5 transform rounded-full bg-white shadow ring-0 transition duration-200 ease-in-out']" />
                </Switch>
              </SwitchGroup>
            </div>
          </div>
        </div>

        <div v-if="activeNav.name=='Demographics'" id="Demographics">

          <div class="mt-6 border p-3 rounded-md">
            <label for="populationSize" class="block text-sm font-medium text-gray-700">Population Size</label>
            <div class="flex space-x-3">
              <input
                style="-webkit-appearance: none;"
                class="bg-gray-200 h-1.5 rounded m-auto w-10/12"
                type="range"
                min="0"
                max="100"
                v-model="populationSize"
              >
              <input id="populationSize" name="populationSize" type="number"
                v-model="populationSize"
                class="block w-2/12 rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
          </div>
  
          <div class="mt-6 border p-3 rounded-md">
            <label for="seed" class="block text-sm font-medium text-gray-700">Seed</label>
            <div class="flex space-x-3">
              <input
                style="-webkit-appearance: none;"
                class="bg-gray-200 h-1.5 rounded m-auto w-10/12"
                type="range"
                min="0"
                max="100"
                v-model="seed"
              >
              <input id="seed" name="seed" type="number"
                v-model="seed"
                class="block w-2/12 rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
          </div>

          <div class="mt-6 border p-3 rounded-md">
            <label for="middleNameProbability" class="block text-sm font-medium text-gray-700">Middle Name Probability</label>
            <div class="flex space-x-3">
              <input
                style="-webkit-appearance: none;"
                class="bg-gray-200 h-1.5 rounded m-auto w-10/12"
                type="range"
                min="0"
                max="100"
                v-model="middleNameProbability"
              >
              <input id="middleNameProbability" name="middleNameProbability" type="number"
                v-model="middleNameProbability"
                class="block w-2/12 rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
          </div>

          <div class="flex space-x-5">
            <div class="mt-6 w-1/2 border p-3 rounded-md">
              <label for="minAge" class="block text-sm font-medium text-gray-700">Minimum Age</label>
              <div class="flex space-x-3">
                <input
                  style="-webkit-appearance: none;"
                  class="bg-gray-200 h-1.5 rounded m-auto w-10/12"
                  type="range"
                  min="0"
                  max="100"
                  v-model="minAge"
                >
                <input id="minAge" name="minAge" type="number"
                  v-model="minAge"
                  class="block w-2/12 rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
            <div class="mt-6 w-1/2 border p-3 rounded-md">
              <label for="maxAge" class="block text-sm font-medium text-gray-700">Maximum Age</label>
              <div class="flex space-x-3">
                <input
                  style="-webkit-appearance: none;"
                  class="bg-gray-200 h-1.5 rounded m-auto w-10/12"
                  type="range"
                  min="0"
                  max="100"
                  v-model="maxAge"
                >
                <input id="maxAge" name="maxAge" type="number"
                  v-model="maxAge"
                  class="block w-2/12 rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
              </div>
            </div>
          </div>

          <div class="mt-6 border w-1/2 p-3 rounded-md">
            <label for="gender" class="block text-sm font-medium leading-6 text-gray-900">Gender</label>
            <RadioGroup v-model="gender" class="mt-1">
              <div class="grid grid-cols-3 gap-3 sm:grid-cols-6">
                <RadioGroupOption as="template" value="R" v-slot="{ active, checked }">
                  <div :class="[active ? 'ring-2 ring-blue-600 ring-offset-2' : '', checked ? 'bg-blue-600 text-white hover:bg-blue-500' : 'ring-1 ring-inset cursor-pointer ring-gray-300 bg-white text-gray-900 hover:bg-gray-50', 'flex items-center focus:outline-none focus:ring-0 justify-center rounded-md py-2 px-2 text-sm font-base uppercase sm:flex-1']">
                    <RadioGroupLabel as="span">Random</RadioGroupLabel>
                  </div>
                </RadioGroupOption>
                <RadioGroupOption as="template" value="M" v-slot="{ active, checked }">
                  <div :class="[active ? 'ring-2 ring-blue-600 ring-offset-2' : '', checked ? 'bg-blue-600 text-white hover:bg-blue-500' : 'ring-1 ring-inset cursor-pointer ring-gray-300 bg-white text-gray-900 hover:bg-gray-50', 'flex items-center focus:outline-none focus:ring-0 justify-center rounded-md py-2 px-2 text-sm font-base uppercase sm:flex-1']">
                    <RadioGroupLabel as="span">Male</RadioGroupLabel>
                  </div>
                </RadioGroupOption>
                <RadioGroupOption as="template" value="F" v-slot="{ active, checked }">
                  <div :class="[active ? 'ring-2 ring-blue-600 ring-offset-2' : '', checked ? 'bg-blue-600 text-white hover:bg-blue-500' : 'ring-1 ring-inset cursor-pointer ring-gray-300 bg-white text-gray-900 hover:bg-gray-50', 'flex items-center focus:outline-none focus:ring-0 justify-center rounded-md py-2 px-2 text-sm font-base uppercase sm:flex-1']">
                    <RadioGroupLabel as="span">Female</RadioGroupLabel>
                  </div>
                </RadioGroupOption>
              </div>
            </RadioGroup>
          </div>
          
        </div>

        <div v-if="activeNav.name=='Cost'" id="Cost">

          <div class=" mt-6 grid grid-cols-2 gap-3">
            <div class="border p-3 rounded-md">
              <label for="default_procedure_cost" class="block text-sm font-medium text-gray-700">Default Procedure Cost</label>
                <input id="default_procedure_cost" name="default_procedure_cost" type="number"
                  v-model="default_procedure_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>

            <div class="border p-3 rounded-md">
              <label for="default_medication_cost" class="block text-sm font-medium text-gray-700">Default Medication Cost</label>
                <input id="default_medication_cost" name="default_medication_cost" type="number"
                  v-model="default_medication_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
  
            <div class="border p-3 rounded-md">
              <label for="default_encounter_cost" class="block text-sm font-medium text-gray-700">Default Encounter Cost</label>
                <input id="default_encounter_cost" name="default_encounter_cost" type="number"
                  v-model="default_encounter_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
  
            <div class="border p-3 rounded-md">
              <label for="default_immunization_cost" class="block text-sm font-medium text-gray-700">Default Immunization Cost</label>
                <input id="default_immunization_cost" name="default_immunization_cost" type="number"
                  v-model="default_immunization_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>

            <div class="border p-3 rounded-md">
              <label for="default_lab_cost" class="block text-sm font-medium text-gray-700">Default Lab Cost</label>
                <input id="default_lab_cost" name="default_lab_cost" type="number"
                  v-model="default_lab_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>
            
            <div class="border p-3 rounded-md">
              <label for="default_device_cost" class="block text-sm font-medium text-gray-700">Default device Cost</label>
                <input id="default_device_cost" name="default_device_cost" type="number"
                  v-model="default_device_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>

            <div class="border p-3 rounded-md">
              <label for="default_supply_cost" class="block text-sm font-medium text-gray-700">Default supply Cost</label>
                <input id="default_supply_cost" name="default_supply_cost" type="number"
                  v-model="default_supply_cost"
                  class="block w-full rounded-md border-0 px-2 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-1 focus:ring-inset focus:ring-blue-600 sm:text-sm sm:leading-6" />
            </div>

          </div>

        </div>

        <div v-if="activeNav.name=='Modules'" id="Modules">

          <div class="mt-6 border p-3 rounded-md">
            <p class=" text-center text-base text-gray-900">Avaialble Modules</p>
            <div class="mt-4 grid grid-cols-1 gap-y-6 sm:grid-cols-3 sm:gap-x-4">
              <div as="template" >
                <!-- onlyAlive -->
                <label for="onlyAlive" :class="[onlyAlive ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Alive Only</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">Only Include Living People</div>
                      <div :class="[onlyAlive ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ onlyAlive? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="onlyAlive" id="onlyAlive" name="onlyAlive" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!onlyAlive ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- fuzzFhir -->
              <div as="template" >
                <label for="fuzzFhir" :class="[fuzzFhir ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Fuzz Fhir</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">Generate Invalid Fhir Data</div>
                      <div :class="[fuzzFhir ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ fuzzFhir? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="fuzzFhir" id="fuzzFhir" name="fuzzFhir" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!fuzzFhir ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- semanticallyInvalidModules -->
              <div as="template" >
                <label for="semanticallyInvalidModules" :class="[semanticallyInvalidModules ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Semantically Invalid Modules</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">Use Semantically Invalid Modules</div>
                      <div :class="[semanticallyInvalidModules ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ semanticallyInvalidModules? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="semanticallyInvalidModules" id="semanticallyInvalidModules" name="semanticallyInvalidModules" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!semanticallyInvalidModules ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- includeEncounterModule -->
              <div as="template" >
                <label for="includeEncounterModule" :class="[includeEncounterModule ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Encounter Module</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">Includes Encounter</div>
                      <div :class="[includeEncounterModule ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ includeEncounterModule? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="includeEncounterModule" id="includeEncounterModule" name="includeEncounterModule" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!includeEncounterModule ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- includeDiagnosisModule -->
              <div as="template" >
                <label for="includeDiagnosisModule" :class="[includeDiagnosisModule ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Diagnosis Module</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">includes Condition</div>
                      <div :class="[includeDiagnosisModule ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ includeDiagnosisModule? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="includeDiagnosisModule" id="includeDiagnosisModule" name="includeDiagnosisModule" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!includeDiagnosisModule ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- includeLabsModule -->
              <div as="template" >
                <label for="includeLabsModule" :class="[includeLabsModule ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Labs Module</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">includes DiagnosticReport, Observation, ServiceRequest</div>
                      <div :class="[includeLabsModule ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ includeLabsModule? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="includeLabsModule" id="includeLabsModule" name="includeLabsModule" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!includeLabsModule ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- includePersonModule -->
              <div as="template" >
                <label for="includePersonModule" :class="[includePersonModule ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Person Module</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">Includes Patient, Research Subject, Observation (Vital Status)</div>
                      <div :class="[includePersonModule ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ includePersonModule? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="includePersonModule" id="includePersonModule" name="includePersonModule" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!includePersonModule ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
              <!-- includeMedicationModule -->
              <div as="template" >
                <label for="includeMedicationModule" :class="[includeMedicationModule ? 'border-green-600 ring-1 ring-green-600' : 'border-gray-300', 'relative flex cursor-pointer rounded-lg border bg-white p-4 shadow-sm focus:outline-none']">
                  <span class="flex flex-1">
                    <span class="flex flex-col">
                      <div as="span" class="block underline text-sm font-medium text-gray-900">Medication Module</div>
                      <div as="span" class="mt-1 flex items-center text-sm text-gray-500">includes MedicationRequest, MedicationAdministration, MedicationStatement</div>
                      <div :class="[includeMedicationModule ? 'bg-green-100 text-green-800' : 'bg-red-100 text-red-800', 'rounded-full w-fit px-2.5 py-0.5 mt-6 text-sm font-medium text-gray-900']">
                        {{ includeMedicationModule? 'Active' : 'Not Active' }}
                      </div>
                    </span>
                  </span>
                  <input hidden v-model="includeMedicationModule" id="includeMedicationModule" name="includeMedicationModule" type="checkbox" class="h-4 w-4 rounded border-gray-300 text-green-600 focus:ring-green-600" />
                  <CheckCircleIcon :class="[!includeMedicationModule ? 'invisible' : '', 'h-5 w-5 text-green-600']" aria-hidden="true" />
                </label>
              </div>
            </div>
          </div>

        </div>

      
      </div>
    </div>

    </main>
  </div>
  <div v-if="loading" >
    <GenrateDataLoading :progress="loadingProgress" :loadingData="loadingData" />

  </div>

</template>

<style>
body {
  font-family: 'Inter', sans-serif;
  background-color: #e3e4e8;
}
</style>

<script setup>
import { ref, watch } from 'vue'
import GenrateDataLoading from '../components/GenerateDataLoading.vue'
import AlertDialog from '../components/AlertDialog.vue'
import { FolderIcon, UsersIcon } from '@heroicons/vue/24/outline'
import { CheckCircleIcon } from '@heroicons/vue/20/solid'
import { RadioGroup, RadioGroupLabel, RadioGroupOption, Switch, SwitchDescription, SwitchGroup, SwitchLabel } from '@headlessui/vue'
import { useMainStore } from '@/stores/mainStore'


const navigation = [
  // { name: 'General', icon: HomeIcon },
  { name: 'Demographics', icon: UsersIcon },
  // { name: 'Cost', icon: CurrencyEuroIcon },
  { name: 'Modules', icon: FolderIcon },
]

const activeNav = ref(navigation[0])
const loading = ref(false)
const showResetDialog = ref(false)
const loadingData = ref([])
const loadingProgress = ref(0)
const mainStore = useMainStore()

const exportFhir = ref(true)
const exportMetaData = ref(true)
const exportFhirTransactionBundle = ref(true)
const exportFhirHospital = ref(true)
const exportFhirPractitioner = ref(true)


const populationSize = ref(1);
const seed = ref(null);
const minAge = ref(null);
const maxAge = ref(null);
const gender = ref('R')
const middleNameProbability = ref(80)

const default_procedure_cost = ref(500)
const default_medication_cost = ref(255)
const default_encounter_cost = ref(125)
const default_immunization_cost = ref(136)
const default_lab_cost = ref(100)
const default_device_cost = ref(0)
const default_supply_cost = ref(0)

const onlyAlive = ref(null)
const fuzzFhir = ref(null)
const semanticallyInvalidModules = ref(null)
const includePersonModule = ref(true)
const includeEncounterModule = ref(true)
const includeDiagnosisModule = ref(true)
const includeLabsModule = ref(true)
const includeMedicationModule = ref(true)

const changeNav = (item) => {
  activeNav.value = item
}

const resetFields = () => {
  exportFhir.value = true
  exportMetaData.value = true
  exportFhirTransactionBundle.value = true
  exportFhirHospital.value = true
  exportFhirPractitioner.value = true

  populationSize.value = 1
  seed.value = null
  minAge.value = null
  maxAge.value = null
  gender.value = 'R'
  middleNameProbability.value = 80

  default_procedure_cost.value = 500
  default_medication_cost.value = 255
  default_encounter_cost.value = 125
  default_immunization_cost.value = 136
  default_lab_cost.value = 100
  default_device_cost.value = 0
  default_supply_cost.value = 0

  onlyAlive.value = null
  fuzzFhir.value = null
  semanticallyInvalidModules.value = null
  includePersonModule.value = true
  includeEncounterModule.value = true
  includeDiagnosisModule.value = true
  includeLabsModule.value = true
  includeMedicationModule.value = true
}

watch(includePersonModule,newValue => {
  if(newValue == false&&(includeDiagnosisModule.value || includeLabsModule.value || includeMedicationModule.value)){
    includePersonModule.value = true
    includeEncounterModule.value = true
  }
})

watch(includeEncounterModule,newValue => {
  if(newValue == false&&(includeDiagnosisModule.value || includeLabsModule.value || includeMedicationModule.value)){
    includePersonModule.value = true
    includeEncounterModule.value = true
  }
})

watch(includeDiagnosisModule,newValue => {
  if(newValue == true){
    includePersonModule.value = true
    includeEncounterModule.value = true
  }
})

watch(includeLabsModule,newValue => {
  if(newValue == true){
    includePersonModule.value = true
    includeEncounterModule.value = true
  }
})

watch(includeMedicationModule,newValue => {
  if(newValue == true){
    includePersonModule.value = true
    includeEncounterModule.value = true
  }
})

watch(populationSize,newValue => {
  if(newValue == '') populationSize.value = null
  else if(newValue < 0 ){
    populationSize.value = Math.abs(newValue)
  }
})

watch(seed,newValue => {
  if(newValue == '') seed.value = null
  else if(newValue < 0 ){
    seed.value = Math.abs(newValue)
  }
})

watch(middleNameProbability,newValue => {
  if(newValue == '') middleNameProbability.value = null
  else if(newValue < 0 ){
    middleNameProbability.value = Math.abs(newValue)
  }
  else if(newValue > 100){
    middleNameProbability.value = 100
  }
})

watch(minAge,newValue => {
  if(newValue == '') minAge.value = null
  else {
    if(maxAge.value && newValue>=maxAge.value){
      maxAge.value = parseInt(newValue) + 1
    }
  }
})

watch(maxAge,newValue => {
  if(newValue == '') maxAge.value = null
  else{
    if(minAge.value && newValue<=minAge.value){
      minAge.value = parseInt(newValue) - 1
    }
  }
})

watch(default_procedure_cost,newValue => {
  if(newValue == '') default_procedure_cost.value = null
  else if(newValue < 0 ){
    default_procedure_cost.value = Math.abs(newValue)
  }
})

watch(default_medication_cost,newValue => {
  if(newValue == '') default_medication_cost.value = null
  else if(newValue < 0 ){
    default_medication_cost.value = Math.abs(newValue)
  }
})

watch(default_encounter_cost,newValue => {
  if(newValue == '') default_encounter_cost.value = null
  else if(newValue < 0 ){
    default_encounter_cost.value = Math.abs(newValue)
  }
})

watch(default_immunization_cost,newValue => {
  if(newValue == '') default_immunization_cost.value = null
  else if(newValue < 0 ){
    default_immunization_cost.value = Math.abs(newValue)
  }
})

watch(default_lab_cost,newValue => {
  if(newValue == '') default_lab_cost.value = null
  else if(newValue < 0 ){
    default_lab_cost.value = Math.abs(newValue)
  }
})

watch(default_device_cost,newValue => {
  if(newValue == '') default_device_cost.value = null
  else if(newValue < 0 ){
    default_device_cost.value = Math.abs(newValue)
  }
})

watch(default_supply_cost,newValue => {
  if(newValue == '') default_supply_cost.value = null
  else if(newValue < 0 ){
    default_supply_cost.value = Math.abs(newValue)
  }
})

watch(mainStore.getGenerateLogs,newValue => {
  loadingData.value.push(...newValue)
})



let params = new URLSearchParams();

const validateParams = () => {
  params = new URLSearchParams();
  params.append('userID',mainStore.getUserID)
  params.append('exportFhir', exportFhir.value)
  params.append('exportMetaData', exportMetaData.value)
  params.append('exportFhirTransactionBundle', exportFhirTransactionBundle.value)
  params.append('exportFhirHospital', exportFhirHospital.value)
  params.append('exportFhirPractitioner', exportFhirPractitioner.value)
  params.append('includePersonModule', includePersonModule.value)
  params.append('includeEncounterModule', includeEncounterModule.value)
  params.append('includeDiagnosisModule', includeDiagnosisModule.value)
  params.append('includeLabsModule', includeLabsModule.value)
  params.append('includeMedicationModule', includeMedicationModule.value)

  if(populationSize.value && populationSize.value > 0) params.append('populationSize', populationSize.value)
  if(seed.value && seed.value > 0) params.append('seed', seed.value)
  if(minAge.value && minAge.value > 0) params.append('minAge', minAge.value)
  if(maxAge.value && maxAge.value > 0) params.append('maxAge', maxAge.value)
  if(gender.value && gender.value != 'R') params.append('gender',gender.value)
  if(middleNameProbability.value && middleNameProbability.value != 80) params.append('middleNameProbability', middleNameProbability.value/100)

  if(default_procedure_cost.value && default_procedure_cost.value != 500) params.append('default_procedure_cost', default_procedure_cost.value)
  if(default_medication_cost.value && default_medication_cost.value != 255) params.append('default_medication_cost', default_medication_cost.value)
  if(default_encounter_cost.value && default_encounter_cost.value != 125) params.append('default_encounter_cost', default_encounter_cost.value)
  if(default_immunization_cost.value && default_immunization_cost.value != 136) params.append('default_immunization_cost', default_immunization_cost.value)
  if(default_lab_cost.value && default_lab_cost.value != 100) params.append('default_lab_cost', default_lab_cost.value)
  if(default_device_cost.value && default_device_cost.value != 0) params.append('default_device_cost', default_device_cost.value)
  if(default_supply_cost.value && default_supply_cost.value != 0) params.append('default_supply_cost', default_supply_cost.value)

  if(onlyAlive.value && onlyAlive.value != false) params.append('onlyAlive', onlyAlive.value)
  if(fuzzFhir.value && fuzzFhir.value != false) params.append('fuzzFhir', fuzzFhir.value)
  if(semanticallyInvalidModules.value && semanticallyInvalidModules.value != false) params.append('semanticallyInvalidModules', semanticallyInvalidModules.value)
}


const apiEndpoint = '/rest/v1/generate';
const generateData = async () => {
  loading.value = true
  loadingData.value = []
  loadingProgress.value = 0
  loadingData.value.push('Validating Parameters')
  validateParams()
  loadingData.value.push('Done: Parameters Validations')
  loadingProgress.value = 10
  loadingData.value.push('Generating Data')
  loadingProgress.value = 40
  await fetch(`${apiEndpoint}?${params}`, {
    method: 'POST',
    headers: {
      'Accept': 'application/zip',
      'Content-Type': 'application/zip'
    }
  })
  .then(async response => {
    loadingProgress.value = 60
    if (!response.ok) {
      throw new Error('Network response was not ok');
    }
    const contentDisposition = response.headers.get('Content-Disposition');
    let filename = 'download.zip';  // Default filename.
    if (contentDisposition) {
      const match = contentDisposition.match(/filename="(.+?)"/);
      if (match && match[1]) filename = match[1];
    }
    return response.blob().then(blob => {
      loadingProgress.value = 100
      loadingData.value.push('Done: Generating Data')
      const link = document.createElement('a');
      link.href = window.URL.createObjectURL(blob);
      link.setAttribute('download', filename);
      document.body.appendChild(link);
      loading.value = false
      link.click();
      link.remove();
    });
  })
  .catch(error => {
    console.error('Download error:', error);
  });
}

</script>