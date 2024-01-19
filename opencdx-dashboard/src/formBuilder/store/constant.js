// theme constant
export const gridSpacing = 3;
export const drawerWidth = 260;
export const appDrawerWidth = 320;
export const categories = [
    'General',
    'Timing',
    'Context',
    'Value and Interpretation',
    'Additional Information',
    'Reference Range',
    'Relationship'
];

export const observationAttributes = [
    // General Attributes
    { observationCategory: 'General', label: 'identifier', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'code', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'subject', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'focus', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'General', label: 'encounter', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Timing Attributes
    { observationCategory: 'Timing', label: 'effective_x_', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Timing', label: 'issued', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Context Attributes
    { observationCategory: 'Context', label: 'performer', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Context', label: 'encounter', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },

    // Value and Interpretation Attributes
    {
        observationCategory: 'Value and Interpretation',
        label: 'value_x_',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Value and Interpretation',
        label: 'dataAbsentReason',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Value and Interpretation',
        label: 'interpretation',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Additional Information Attributes
    {
        observationCategory: 'Additional Information',
        label: 'note',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'bodySite',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'bodyStructure',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'method',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'specimen',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Additional Information',
        label: 'device',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Reference Range Attributes
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.low',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.high',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.normalValue',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.type',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.appliesTo',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.age',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Reference Range',
        label: 'referenceRange.text',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },

    // Relationship Attributes
    { observationCategory: 'Relationship', label: 'hasMember', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Relationship', label: 'derivedFrom', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    { observationCategory: 'Relationship', label: 'component', options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' } },
    {
        observationCategory: 'Relationship',
        label: 'component.code',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.value_x_',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.dataAbsentReason',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.interpretation',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    },
    {
        observationCategory: 'Relationship',
        label: 'component.referenceRange',
        options: { 1: 'Code Option 1', 2: 'Code Option 2', 3: 'Code Option 3' }
    }
];

export const systemVariables = {
    time: {
        lowerBound: '${{system.util.epoc("${{application.statement.documented.start.time}}")}}',
        semantic: 'Seconds | ${{upperbound - lowerbound}}',
        resolution: '1 second',
        upperBound: '${{system.util.epoc("${{application.statement.documented.end.time}}")}}',
        lowerBoundOptions: 'yes',
        upperBoundOptions: 'yes'
    },
    subjectOfRecord: {
        id: '${{participant.uuid}}',
        practitionerValue: '${{participant.id}}',
        code: ''
    },
    authors: [
        {
            id: '${{paractitioner[0].uuid}}',
            practitionerValue:
                '"Practitioner": {"reference": "Practitioner/${{paractitioner[0].id}}", "role": "Practitioner/${{paractitioner[0].role}}"',
            code: '${{paractitioner[0].role[0].code}}, ${{paractitioner[0].role[1].code}}'
        },
        {
            id: '{{paractitioner[1].uuid}}',
            practitionerValue:
                '"Practitioner": {"reference": "Practitioner/${{paractitioner[1].id}}", "role": "Practitioner/${{paractitioner[1].role}}"',
            code: '${{paractitioner[1].role[0].code}}, ${{paractitioner[1].role[1].code}}'
        }
    ],
    subject_of_information: {
        subjectOfRecord: '{ id: ${{participant.id}} }'
    },
    associatedStatement: [
        { id: 'JHMH-EDDH-6591-HVQTY-GFSM', semantic: 'TBD - a Precondition | an interpretation | a component' },
        { id: 'ZDWC-WZDU-2801-DDHZI-KELI', semantic: 'TBD - a Precondition | an interpretation | a component' },
        { id: 'JNMC-FXBC-7589-MTEQD-IMBB', semantic: 'TBD - a Precondition | an interpretation | a component' },
        { id: 'BNAJ-FCSY-1342-GPXZD-QMCO', semantic: 'Precondition' },
        { id: 'GEEI-TJBI-9441-VIAQB-QOYU', semantic: 'Precondition' }
    ],
    topic: {
        observationProcedure: {
            method: 'Examination - action',
            hasFocus: 'On examination - Systolic blood pressure reading',
            procedureSiteDirect: 'Structure of right brachial artery',
            usingDevice: 'Blood pressure cuff adult size'
        }
    },
    type: {
        expressionType: 'simple',
        expressionLanguage: 'local',
        expressionValue: 'performed',
        expressionDescription: 'Measurement action has been performed.'
    },
    circumstanceChoice: [{ healthRisk: 'XXXXX ${{rules.engine.calculated[circumstanceChoice.result]}}' }],
    status: {
        expressionType: 'simple',
        expressionLanguage: 'local',
        expressionValue: 'performed',
        expressionDescription: 'Measurement action has been performed.'
    },
    result: {
        lowerBound: '90',
        semantic:
            '[${{getElementById("3079919224534").value}}, ${{getElementById("3079919224534").value}}] ${{getElementById("3079919224534").unit}}',
        resolution: '1 mmHg',
        upperBound: '120',
        lowerBoundOptions: 'yes',
        upperBoundOptions: 'yes'
    },
    rangeMeasure: {
        lowerBound: '90',
        semantic: 'Systolic blood pressure measurement',
        resolution: '1 mmHg',
        upperBound: '120',
        lowerBoundOptions: 'yes',
        upperBoundOptions: 'yes'
    },
    rangeParticipant: [
        {
            id: '${{paractitioner[0].uuid}}',
            practitionerValue:
                '"Practitioner": {"reference": "Practitioner/${{paractitioner[0].id}}", "role": "Practitioner/${{paractitioner[0].role}}"',
            code: '${{paractitioner[0].role[0].code}}, ${{paractitioner[0].role[1].code}}'
        },
        {
            id: '{{paractitioner[1].uuid}}',
            practitionerValue:
                '"Practitioner": {"reference": "Practitioner/${{paractitioner[1].id}}", "role": "Practitioner/${{paractitioner[1].role}}"',
            code: '${{paractitioner[1].role[0].code}}, ${{paractitioner[1].role[1].code}}'
        }
    ],
    timingMeasure: {
        lowerBound: '${{system.util.epoc("${{application.statement.measurement.start.time}}")}}',
        semantic: 'Seconds | ${{upperbound - lowerbound}}',
        resolution: '1 second',
        upperBound: '${{system.util.epoc("${{application.statement.measurement.end.time}}")}}',
        lowerBoundOptions: 'yes',
        upperBoundOptions: 'yes'
    },
    purpose: [
        {
            procedureId: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].id}}',
            procedureName: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].name}}',
            focus: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].code}} | ${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].procedure.name}} (procedure)'
        },
        {
            procedureId: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].id}}',
            procedureName: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].name}}',
            focus: '${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].code}} | ${{application.anfstatement.cicumstance.performancecircumstance.purpose[0].procedure.name}} (procedure)'
        }
    ]
};

// console.log(JSON.stringify(value, null, 2));
