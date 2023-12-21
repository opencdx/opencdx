import PropTypes from 'prop-types';

// material-ui
import {
    Box,
    Button,
    Divider,
    Grid,
    LinearProgress,
    List,
    ListItemButton,
    ListItemIcon,
    ListItemSecondaryAction,
    ListItemText,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableRow,
    Typography
} from '@mui/material';

// project imports
import useAuth from 'hooks/useAuth';
import Avatar from 'ui-component/extended/Avatar';
import SubCard from 'ui-component/cards/SubCard';
import { gridSpacing } from 'store/constant';

// assets
import { IconEdit } from '@tabler/icons-react';
import PhonelinkRingTwoToneIcon from '@mui/icons-material/PhonelinkRingTwoTone';
import PinDropTwoToneIcon from '@mui/icons-material/PinDropTwoTone';
import MailTwoToneIcon from '@mui/icons-material/MailTwoTone';

import Avatar3 from 'assets/images/users/avatar-1.png';
import { useNavigate } from 'react-router-dom';

// progress
function LinearProgressWithLabel({ value, ...others }) {
    return (
        <Box
            sx={{
                display: 'flex',
                alignItems: 'center'
            }}
        >
            <Box
                sx={{
                    width: '100%',
                    mr: 1
                }}
            >
                <LinearProgress value={value} {...others} />
            </Box>
            <Box sx={{ minWidth: 35 }}>
                <Typography variant="body2" color="textSecondary">{`${Math.round(value)}%`}</Typography>
            </Box>
        </Box>
    );
}

LinearProgressWithLabel.propTypes = {
    value: PropTypes.number
};

// personal details table
/** names Don&apos;t look right */
function createData(name, calories, fat, carbs, protein) {
    return { name, calories, fat, carbs, protein };
}

// ==============================|| Profile 1 - Profile1 ||============================== //

const Profile1 = () => {
    const { user } = useAuth();
    const navigate = useNavigate();

    const rows = [
        createData('Full Name', ':', user?.name),

        //createData('Full Name', ':', 'First Middle Last'),
        createData('Gender', ':', 'Male'),

        createData('Mobile Number', ':', '+123456789'),
        createData('Home Number', ':', '+123456789'),
        createData('Fax Number', ':', '+123456789'),

        createData('Date Of Birth', ':', '20/10/1970'),
        createData('Place Of Birth', ':', 'City, CA, USA'),
        createData('Primary Address', ':', '101 Main Street, City, CA, USA, 12345'),
        createData('Dependent ID', ':', '655787ff36bf9e6b6453412b, 655787ff36bf9e6b6453412c')
    ];

    return (
        <Grid container spacing={gridSpacing}>
            <Grid item lg={6} xs={12}>
                <Grid container spacing={gridSpacing}>
                    <Grid item xs={12}>
                        <SubCard
                            title={
                                <Grid container spacing={2} alignItems="center">
                                    <Grid item>
                                        <Avatar alt="User 1" src={Avatar3} />
                                    </Grid>
                                    <Grid item xs zeroMinWidth>
                                        <Typography align="left" variant="subtitle1">
                                            {/* {user?.name} */}
                                            Karthick Raja Murugan
                                        </Typography>
                                        <Typography name="userId" align="left" variant="subtitle2">
                                            User Id: 655787ff36bf9e6b64534129
                                        </Typography>
                                    </Grid>
                                </Grid>
                            }
                        >
                            <List component="nav" aria-label="main mailbox folders">
                                <ListItemButton>
                                    <ListItemIcon>
                                        <MailTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                    </ListItemIcon>
                                    <ListItemText primary={<Typography variant="subtitle1">National Health Id</Typography>} />
                                    <ListItemSecondaryAction>
                                        <Typography name="nationalHealthId" variant="subtitle2" align="right">
                                            f4563dd7-9136-4446-835b-a051d5c39b3d
                                        </Typography>
                                    </ListItemSecondaryAction>
                                </ListItemButton>
                                <Divider />
                                <ListItemButton>
                                    <ListItemIcon>
                                        <MailTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                    </ListItemIcon>
                                    <ListItemText primary={<Typography variant="subtitle1">Email</Typography>} />
                                    <ListItemSecondaryAction>
                                        <Typography variant="subtitle2" align="right">
                                            {/* {user?.name} */}
                                            test@opencdx.org
                                        </Typography>
                                    </ListItemSecondaryAction>
                                </ListItemButton>
                                <Divider />
                                <ListItemButton>
                                    <ListItemIcon>
                                        <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                    </ListItemIcon>
                                    <ListItemText primary={<Typography variant="subtitle1">Mobile Number</Typography>} />
                                    <ListItemSecondaryAction>
                                        <Typography variant="subtitle2" align="right">
                                            (+99) 9999 999 999
                                        </Typography>
                                    </ListItemSecondaryAction>
                                </ListItemButton>
                                <Divider />
                                <ListItemButton>
                                    <ListItemIcon>
                                        <PinDropTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                    </ListItemIcon>
                                    <ListItemText primary={<Typography variant="subtitle1">Home Number</Typography>} />
                                    <ListItemSecondaryAction>
                                        <Typography variant="subtitle2" align="right">
                                            (+99) 9999 999 999
                                        </Typography>
                                    </ListItemSecondaryAction>
                                </ListItemButton>
                            </List>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Pharmacy Details">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Pharmacy Name</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Pharmacyname" variant="subtitle2">
                                                Pharmacy Name {/* {pharmacydetails?.pharmacyname} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Pharmacy Address</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="pharmacyaddress" variant="subtitle2">
                                                101 main street,CA,12345, USA{/* {pharmacydetails?.pharmacyaddress} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Mobile number</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="mobilenumber" variant="subtitle2">
                                                1234567890 {/* {pharmacydetails?.mobilenumber} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Home number</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="homenumber" variant="subtitle2">
                                                1234567890 {/* {pharmacydetails?.homenumber} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Fax number</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Faxnumber" variant="subtitle2">
                                                1234567890{/* {pharmacydetails?.faxnumber} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Email </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Email" variant="subtitle2">
                                                test@opencdx.org{/* {pharmacydetails?.email} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Vaccine Administered">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Administration Date</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="administrationdate" variant="subtitle2">
                                                2021/06/01{/* {vaccineadministered?.administrationdate} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Fips</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Fips" variant="subtitle2">
                                                12345{/* {vaccineadministered?.fips} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Locality</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Locality" variant="subtitle2">
                                                CA {/* {vaccineadministered?.locality} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Health District </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Healthdistrict" variant="subtitle2">
                                                District {/* {vaccineadministered?.healthdistrict} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Faclity Type</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Faclitytype" variant="subtitle2">
                                                Clinic{/* {vaccineadministered?.faclitytype} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Manufacturer </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Manufacturer" variant="subtitle2">
                                                655787ff36bf9e6b6453412a{/* {vaccineadministered?.manufacturer} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Dose Number </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Dosenumber" variant="subtitle2">
                                                20{/* {vaccineadministered?.Dosenumber} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Vaccine Type </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Vaccinetype" variant="subtitle2">
                                                COVID-19{/* {vaccineadministered?.Vaccinetype} */}{' '}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Known Allergies">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Allergen</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="allergen" variant="subtitle2">
                                                Evergreen Trees {/* {knownallergies?.allergen} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Reaction</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Reaction" variant="subtitle2">
                                                Respiratory Distress{/* {knownallergies?.reaction} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Is Severe</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Isserve" variant="subtitle2">
                                                True {/* {knownallergies?.Isserve} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">On set Date </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="onsetdate" variant="subtitle2">
                                                1975/12/20{/* {knownallergies?.onsetdate} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1"> Last Occurrence </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="lastoccurrence" variant="subtitle2">
                                                1976/12/25{/* {knownallergies?.lastoccurrence} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Notes </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="notes" variant="subtitle2">
                                                Chrirstmas Trees {/* {knownallergies?.notes} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Current Medications">
                            <Grid container spacing={2}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Name</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="name" variant="subtitle2">
                                                Singular
                                                {/* {currentmedications?.name} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Dosage</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="dosage" variant="subtitle2">
                                                5 mg{/* {currentmedications?.dosage} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Instructions</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Instructions" variant="subtitle2">
                                                Take 1 pill at Night {/* {currentmedications?.Instructions} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Route Of Administration </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Routeofadministration" variant="subtitle2">
                                                Oral{/* {currentmedications?.Routeofadministration} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1"> Frequency </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Frequency" variant="subtitle2">
                                                Daily{/* {currentmedications?.Frequency} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Start Date </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="startdate" variant="subtitle2">
                                                1976/12/26 {/* {currentmedications?.stratdate} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Prescribing Doctor </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="prescribingdoctor" variant="subtitle2">
                                                Dr. OpenCDX {/* {currentmedications?.prescribingdoctor} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Pharmacy </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="pharmacy" variant="subtitle2">
                                                Pharmacy {/* {currentmedications?.pharmacy} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Is Prescription </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Isprescription" variant="subtitle2">
                                                True {/* {currentmedications?.Isprescription} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                </Grid>
            </Grid>
            <Grid item lg={6} xs={12}>
                <Grid container spacing={gridSpacing}>
                    <Grid item xs={12}>
                        <SubCard
                            title="Personal Details"
                            secondary={
                                <Button aria-label="Edit Details">
                                    <IconEdit stroke={1.5} size="20px" onClick={() => {
                                        navigate('/user/edit-profile'); // Replace '/edit-profile' with the desired URL of the edit-profile page
                                    }} />
                                </Button>
                            }
                        >
                            <Grid container spacing={2}>
                                <Divider sx={{ pt: 1 }} />
                                <Grid item xs={12}>
                                    <TableContainer>
                                        <Table
                                            sx={{
                                                '& td': {
                                                    borderBottom: 'none'
                                                }
                                            }}
                                            size="small"
                                        >
                                            <TableBody>
                                                {rows.map((row) => (
                                                    <TableRow key={row.name}>
                                                        <TableCell variant="head">{row.name}</TableCell>
                                                        <TableCell>{row.calories}</TableCell>
                                                        <TableCell>{row.fat}</TableCell>
                                                    </TableRow>
                                                ))}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Communication">
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Language</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="language" variant="subtitle2">
                                                En{/* {communication?.language} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12} sx={{ display: { xs: 'block', sm: 'none' } }}>
                                    <Divider />
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Preferred</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="prefered" variant="subtitle2">
                                                True{/* {communication?.prefered} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Time Zone</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Timezone" variant="subtitle2">
                                                EST{/* {communication?.Timezone} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>

                    <Grid item xs={12}>
                        <SubCard title="Demographics">
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Ethnicity</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="ethnicity" variant="subtitle2">
                                                Ethnicity{/* {Demographics?.ethnicity} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12} sx={{ display: { xs: 'block', sm: 'none' } }}>
                                    <Divider />
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Race</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="race" variant="subtitle2">
                                                Race{/* {Demographics?.race} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Nationality</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="nationality" variant="subtitle2">
                                                USA{/* {Demographics?.nationality} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Gender</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="gender" variant="subtitle2">
                                                Male{/* {Demographics?.gender} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Education">
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Degree</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="degree" variant="subtitle2">
                                                BA {/* {education?.degree} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12} sx={{ display: { xs: 'block', sm: 'none' } }}>
                                    <Divider />
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Institution</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Instituation" variant="subtitle2">
                                                University{/* {education?.Instituation} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Start Date</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="startdate" variant="subtitle2">
                                                1992/08/01{/* {education?.startdate} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Completion Date</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="completiondate" variant="subtitle2">
                                                1996/05/30{/* {education?.completiondate} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Employee Identity">
                            <Grid container spacing={1}>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">OrganizationId</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="organizationId" variant="subtitle2">
                                                OrganizationId {/* {emplaoyeeIdentity?.organizationId} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12} sx={{ display: { xs: 'block', sm: 'none' } }}>
                                    <Divider />
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Workspace Id</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="workspaceId" variant="subtitle2">
                                                Workspace Id {/* {employeeIdentity?.Id} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Employee Id</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="employeeId" variant="subtitle2">
                                                Employee Id {/* {employeeIdentity?.employeeId} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Identity Verified</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="identityVerified" variant="subtitle2">
                                                True{/* {employeeIdentity?.identityVerified} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                                <Grid item xs={12}>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Status</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="status" variant="subtitle2">
                                                Full Time{/* {employeeIdentity?.status} */}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                </Grid>
                            </Grid>
                        </SubCard>
                    </Grid>
                </Grid>
            </Grid>
        </Grid>
    );
};

export default Profile1;
