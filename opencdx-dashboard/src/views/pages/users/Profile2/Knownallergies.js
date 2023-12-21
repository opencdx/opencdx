// material-ui
import { Grid, TextField, MenuItem } from '@mui/material';

// project imports
import { gridSpacing } from 'store/constant';

// ==============================|| PROFILE 2 - USER PROFILE ||============================== //
const known_allergies_in_usa = [
    'Peanuts',
    'Tree Nuts (e.g., almonds, walnuts)',
    'Milk',
    'Eggs',
    'Wheat',
    'Soy',
    'Fish',
    'Shellfish',
    'Latex',
    'Insect Stings (e.g., bee stings)',
    'Medications (e.g., penicillin)',
    'Pollen',
    'Dust Mites',
    'Mold',
    'Pet Dander',
    'Cockroach Droppings',
    'Certain Fruits (e.g., apples, strawberries)',
    'Sesame',
    'Sulfites',
    'Sunlight (Solar Urticaria)',
    'Cold (Cold Urticaria)',
    'Nickel',
    'Chromium',
    'others'
];

const known_allergic_reactions_in_usa = [
    'Hives (Urticaria)',
    'Swelling (Angioedema)',
    'Itching',
    'Redness or Rash',
    'Runny or Stuffy Nose',
    'Sneezing',
    'Watery Eyes',
    'Coughing',
    'Shortness of Breath',
    'Wheezing',
    'Chest Tightness',
    'Nausea',
    'Vomiting',
    'Diarrhea',
    'Abdominal Pain',
    'Dizziness',
    'Fainting',
    'Anaphylaxis (severe, life-threatening reaction)',
    'Eczema (Atopic Dermatitis)',
    'Asthma Exacerbation',
    'Migraines or Headaches',
    'Joint Pain',
    'Fatigue',
    'others'
];

const knownallegeries = () => (
    <Grid container spacing={gridSpacing}>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a allegerian..." variant="outlined" fullWidth>
                {known_allergies_in_usa?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField select label="Select a reaction..." variant="outlined" fullWidth>
                {known_allergic_reactions_in_usa?.map((template, index) => (
                    <MenuItem key={index} value={template}>
                        {template}
                    </MenuItem>
                ))}
            </TextField>
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Is Severe" defaultValue="True" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="On set Date" defaultValue="1975/12/20" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Last Occurrence" defaultValue=" 1976/12/25" />
        </Grid>
        <Grid item xs={12} sm={6}>
            <TextField fullWidth label="Notes" defaultValue="Chrirstmas Trees" />
        </Grid>
    </Grid>
);

export default knownallegeries;
