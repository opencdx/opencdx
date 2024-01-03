import PropTypes from 'prop-types';
import React from 'react';

// material-ui
import { useTheme } from '@mui/material/styles';
import { Button, CardActions, CardContent, Divider, Grid, Tab, Tabs, Typography } from '@mui/material';

// project imports
import UserProfile from './UserProfile';
import useConfig from 'utils/hooks/useConfig';
import MainCard from 'ui-component/cards/MainCard';
import AnimateButton from 'ui-component/extended/AnimateButton';
import { gridSpacing } from 'utils/store/constant';

import Communication from './Communication';
import CurrentMedications from './CurrentMedications';
import Demographics from './Demographics';
import Education from './Education';
import EmployeeIdentity from './EmployeeIdentity';
import KnownAllegeries from './Knownallergies';
import PharmacyDetails from './PharmacyDetails';
import VaccinationAdministed from './VaccinationAdministed';

// tabs
function TabPanel({ children, value, index, ...other }) {
    return (
        <div role="tabpanel" hidden={value !== index} id={`simple-tabpanel-${index}`} aria-labelledby={`simple-tab-${index}`} {...other}>
            {value === index && <div>{children}</div>}
        </div>
    );
}

TabPanel.propTypes = {
    children: PropTypes.node,
    index: PropTypes.any.isRequired,
    value: PropTypes.any.isRequired
};

function a11yProps(index) {
    return {
        id: `simple-tab-${index}`,
        'aria-controls': `simple-tabpanel-${index}`
    };
}

// tabs option
const tabsOption = [
    {
        label: 'Personal Details'
    },
    {
        label: 'Communication'
    },
    {
        label: 'Current Medications'
    },
    {
        label: 'Demographics'
    },
    {
        label: 'Education'
    },
    {
        label: 'Employee Identity'
    },
    {
        label: 'Known Allegeries'
    },
    {
        label: 'Pharmacy Details'
    },
    {
        label: 'Vaccination Administed'
    }
];

// ==============================|| PROFILE 2 ||============================== //

const Profile2 = () => {
    const theme = useTheme();
    const { borderRadius } = useConfig();
    const [value, setValue] = React.useState(0);

    const handleChange = (event, newValue) => {
        setValue(newValue);
    };

    return (
        <Grid container spacing={gridSpacing}>
            <Grid item xs={12}>
                <MainCard title="Account Settings" content={false}>
                    <Grid container spacing={gridSpacing}>
                        <Grid item xs={12} lg={4}>
                            <CardContent>
                                <Tabs
                                    value={value}
                                    onChange={handleChange}
                                    orientation="vertical"
                                    variant="scrollable"
                                    sx={{
                                        '& .MuiTabs-flexContainer': {
                                            borderBottom: 'none'
                                        },
                                        '& button': {
                                            color: theme.palette.mode === 'dark' ? 'grey.600' : 'grey.900',
                                            minHeight: 'auto',
                                            minWidth: '100%',
                                            py: 1.5,
                                            px: 2,
                                            display: 'flex',
                                            flexDirection: 'row',
                                            alignItems: 'flex-start',
                                            textAlign: 'left',
                                            justifyContent: 'flex-start',
                                            borderRadius: `${borderRadius}px`
                                        },
                                        '& button.Mui-selected': {
                                            color: theme.palette.primary.main,
                                            background: theme.palette.mode === 'dark' ? theme.palette.dark.main : theme.palette.grey[50]
                                        },
                                        '& button > svg': {
                                            marginBottom: '0px !important',
                                            marginRight: 1.25,
                                            marginTop: 1.25,
                                            height: 20,
                                            width: 20
                                        },
                                        '& button > div > span': {
                                            display: 'block'
                                        },
                                        '& > div > span': {
                                            display: 'none'
                                        }
                                    }}
                                >
                                    {tabsOption.map((tab, index) => (
                                        <Tab
                                            key={index}
                                            icon={tab.icon}
                                            label={
                                                <Grid container direction="column">
                                                    <Typography variant="subtitle1" color="inherit">
                                                        {tab.label}
                                                    </Typography>
                                                    <Typography component="div" variant="caption" sx={{ textTransform: 'capitalize' }}>
                                                        {tab.caption}
                                                    </Typography>
                                                </Grid>
                                            }
                                            {...a11yProps(index)}
                                        />
                                    ))}
                                </Tabs>
                            </CardContent>
                        </Grid>
                        <Grid item xs={12} lg={8}>
                            <CardContent
                                sx={{
                                    borderLeft: '1px solid',
                                    borderColor: theme.palette.mode === 'dark' ? theme.palette.background.default : theme.palette.grey[200],
                                    height: '100%'
                                }}
                            >
                                <TabPanel value={value} index={0}>
                                    <UserProfile />
                                </TabPanel>
                                <TabPanel value={value} index={1}>
                                    <Communication />
                                </TabPanel>
                                <TabPanel value={value} index={2}>
                                    <CurrentMedications />
                                </TabPanel>
                                <TabPanel value={value} index={3}>
                                    <Demographics />
                                </TabPanel>
                                <TabPanel value={value} index={4}>
                                    <Education />
                                </TabPanel>
                                <TabPanel value={value} index={5}>
                                    <EmployeeIdentity />
                                </TabPanel>
                                <TabPanel value={value} index={6}>
                                    <KnownAllegeries />
                                </TabPanel>
                                <TabPanel value={value} index={7}>
                                    <PharmacyDetails />
                                </TabPanel>
                                <TabPanel value={value} index={8}>
                                    <VaccinationAdministed />
                                </TabPanel>
                            </CardContent>
                        </Grid>
                    </Grid>
                    <Divider />
                    <CardActions>
                        <Grid container justifyContent="space-between" spacing={0}>
                            <Grid item>
                                {value > 0 && (
                                    <AnimateButton>
                                        <Button variant="outlined" size="large" onClick={(e) => handleChange(e, value - 1)}>
                                            Back
                                        </Button>
                                    </AnimateButton>
                                )}
                            </Grid>
                            <Grid item>
                                {value < 3 && (
                                    <AnimateButton>
                                        <Button variant="contained" size="large" onClick={(e) => handleChange(e, 1 + value)}>
                                            Continue
                                        </Button>
                                    </AnimateButton>
                                )}
                            </Grid>
                        </Grid>
                    </CardActions>
                </MainCard>
            </Grid>
        </Grid>
    );
};

export default Profile2;
