# OpenCDx Dashboard

## Build & Deployment Procedures
### Prerequisites
1. Node 20 installed
2. Enable yarn (`corepack enable`)

### Running locally
1. Run the command `yarn install`
2. Run the command `yarn start`

### Run Cypress tests 
`yarn run cypress open`

## Using the form builder
The form builder uses a form created using the [NLM Form Builder](https://lhcformbuilder.nlm.nih.gov/) as the base. Save a copy of the JSON from the NLM Form Builder that can be used to import into the OpenCDx form builder to configure the ANF statements.

Curret supported question types are integer, string, text, boolean, and choice.

After signing in to the OpenCDx dashboard, the form builder can be found by clicking on the Form Designer menu item on the left of the OpenCDx dashboard or by going to https://localhost:3000/form-builder.

Click upload file on the top left and select the JSON of the questionnaire created from the NLM Form Builder. The list of questions will be displayed and can then be individually configured by expanding the question. The various ANF configurations will be displayed and can be further expanded for additional settings. By default, certain fields will be autopopulated.

Once finished, click save to persist the questionnaire. The updated JSON can also be viewed by clicking ANF Statement JSON on the top of the page.