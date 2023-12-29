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
// import useAuth from 'utils/hooks/useAuth';
import Avatar from 'ui-component/extended/Avatar';
import SubCard from 'ui-component/cards/SubCard';
import { gridSpacing } from 'utils/store/constant';

// assets
import { IconEdit } from '@tabler/icons-react';
import PhonelinkRingTwoToneIcon from '@mui/icons-material/PhonelinkRingTwoTone';
import PinDropTwoToneIcon from '@mui/icons-material/PinDropTwoTone';
import MailTwoToneIcon from '@mui/icons-material/MailTwoTone';

import Avatar3 from 'utils/assets/images/users/avatar-1.png';
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
    // const { user } = useAuth();
    const navigate = useNavigate();
    const user = {
        "userId" : "655787ff36bf9e6b64534128",
        "updatedProfile" : {
          "userId" : "655787ff36bf9e6b64534129",
          "nationalHealthId" : "f4563dd7-9136-4446-835b-a051d5c39b3d",
          "fullName" : {
            "title" : "",
            "firstName" : "First",
            "middleName" : "Middle",
            "lastName" : "last",
            "suffix" : ""
          },
          "contacts" : [ {
            "mobileNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_MOBILE"
            },
            "homeNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_HOME"
            },
            "faxNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_FAX"
            },
            "email" : "test@opencdx.org"
          } ],
          "gender" : "GENDER_MALE",
          "dateOfBirth" : {
            "date" : "1900/01/01"
          },
          "placeOfBirth" : {
            "country" : "USA",
            "state" : "CA",
            "city" : "City"
          },
          "primaryAddress" : {
            "street" : "101 Main Street",
            "city" : "City",
            "state" : "CA",
            "postalCode" : "12345",
            "country" : "USA"
          },
          "photo" : "",
          "communication" : {
            "language" : "EN",
            "preferred" : true,
            "timeZone" : "EST"
          },
          "demographics" : {
            "ethnicity" : "Ethnicity",
            "race" : "Race",
            "nationality" : "USA",
            "gender" : "GENDER_MALE"
          },
          "education" : {
            "entries" : [ {
              "degree" : "BA",
              "institution" : "University",
              "startDate" : "1992/08/01",
              "completionDate" : "1996/05/30"
            } ]
          },
          "employeeIdentity" : {
            "organizationId" : "OrganizationId",
            "workspaceId" : "WorkspaceID",
            "employeeId" : "employeeID",
            "identityVerified" : true,
            "status" : "Full Time"
          },
          "primaryContactInfo" : {
            "mobileNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_MOBILE"
            },
            "homeNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_HOME"
            },
            "faxNumber" : {
              "number" : "1234567890",
              "type" : "PHONE_TYPE_FAX"
            },
            "email" : "test@opencdx.org"
          },
          "billingAddress" : {
            "street" : "101 Main Street",
            "city" : "City",
            "state" : "CA",
            "postalCode" : "12345",
            "country" : "USA"
          },
          "shippingAddress" : {
            "street" : "101 Main Street",
            "city" : "City",
            "state" : "CA",
            "postalCode" : "12345",
            "country" : "USA"
          },
          "emergencyContact" : {
            "relationship" : "Emergency Contact",
            "contactName" : "Name",
            "contactInfo" : {
              "mobileNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_MOBILE"
              },
              "homeNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_HOME"
              },
              "faxNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_FAX"
              },
              "email" : "test@opencdx.org"
            },
            "residenceAddress" : {
              "street" : "101 Main Street",
              "city" : "City",
              "state" : "CA",
              "postalCode" : "12345",
              "country" : "USA"
            },
            "workAddress" : {
              "street" : "101 Main Street",
              "city" : "City",
              "state" : "CA",
              "postalCode" : "12345",
              "country" : "USA"
            }
          },
          "pharmacyDetails" : {
            "pharmacyName" : "Pharmacy Name",
            "pharmacyAddress" : {
              "street" : "101 Main Street",
              "city" : "City",
              "state" : "CA",
              "postalCode" : "12345",
              "country" : "USA"
            },
            "pharmacyContact" : {
              "mobileNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_MOBILE"
              },
              "homeNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_HOME"
              },
              "faxNumber" : {
                "number" : "1234567890",
                "type" : "PHONE_TYPE_FAX"
              },
              "email" : "test@opencdx.org"
            }
          },
          "vaccineAdministered" : [ {
            "administrationDate" : "2021/06/01",
            "fips" : "12345",
            "locality" : "CA",
            "healthDistrict" : "District",
            "facilityType" : "Clinic",
            "manufacturer" : "655787ff36bf9e6b6453412a",
            "doseNumber" : 20,
            "vaccineType" : "COVID-19"
          } ],
          "dependentId" : [ "655787ff36bf9e6b6453412b", "655787ff36bf9e6b6453412c" ],
          "knownAllergies" : [ {
            "allergen" : "Evergreen Trees",
            "reaction" : "Respiratory Distress",
            "isSevere" : true,
            "onsetDate" : "1975/12/20",
            "lastOccurrence" : "1976/12/25",
            "notes" : "Christmas Trees"
          } ],
          "currentMedications" : [ {
            "name" : "Singular",
            "dosage" : "5mg",
            "instructions" : "Take 1 pill at night",
            "routeOfAdministration" : "Oral",
            "frequency" : "Daily",
            "startDate" : "1976/12/26",
            "endDate" : "EOL",
            "prescribingDoctor" : "Dr. OpenCDX",
            "pharmacy" : "Pharmacy",
            "isPrescription" : true
          } ],
          "isActive" : false
        }
      };
    const {
        userId,
        nationalHealthId,
        fullName,
        pharmacyDetails,
        pharmacyAddress,
        vaccineAdministered,
        knownAllergies,
        currentMedications,
        communication,
        education,
        employeeIdentity,
        pharmacyContact,
        demographics
        
    } = user;

    const rows = [
        createData('Full Name', ':', user?.name),
        createData('Gender', ':', user?.gender),
        createData('Mobile Number', ':', user?.mobileNumber),
        createData('Home Number', ':', user?.homeNumber),
        createData('Fax Number', ':', user?.faxNumber),
        createData('Date Of Birth', ':', user?.dateOfBirth),
        createData('Place Of Birth', ':', user?.placeOfBirth),
        createData('Primary Address', ':', user?.primaryAddress),
        createData('Dependent ID', ':', user?.primaryAddress)
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
                                            {fullName?.firstName} {fullName?.middleName} {fullName?.lastName}
                                        </Typography>
                                        <Typography name="userId" align="left" variant="subtitle2">
                                            User Id: {userId}
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
                                            {nationalHealthId}
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
                                            {/* {contacts[0]?.email ?? ''} */}
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
                                            {/* {contacts[0]?.mobileNumber?.number} */}
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
                                            {/* {contacts[0]?.homeNumber?.number} */}
                                        </Typography>
                                    </ListItemSecondaryAction>
                                </ListItemButton>
                            </List>
                        </SubCard>
                    </Grid>
                    <Grid item xs={12}>
                        <SubCard title="Pharmacy Details">
                            <Grid container spacing={2}>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Pharmacy Name</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="Pharmacyname" variant="subtitle2">
                                                {pharmacyDetails?.pharmacyName}
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
                                </Grid>
                            </Grid>
                            <Divider />

                            <Grid container>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Pharmacy Address</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="pharmacyaddress" variant="subtitle2">
                                                {pharmacyAddress?.street} {pharmacyAddress?.city} {pharmacyAddress?.state}
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
                                </Grid>
                            </Grid>
                            <Divider />

                            <Grid container>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Mobile number</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="mobilenumber" variant="subtitle2">
                                                {pharmacyContact?.mobilenumber.number}
                                                {/* {pharmacydetails?.mobilenumber} */} 
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
                                </Grid>
                            </Grid>
                            <Divider />

                            <Grid container>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Home number</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="homenumber" variant="subtitle2">
                                                {/* {pharmacydetails?.homenumber} */}
                                                {pharmacyContact?.homenumber.number}
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
                                </Grid>
                            </Grid>
                            <Divider />

                            <Grid container>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Fax number</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="Faxnumber" variant="subtitle2">
                                                {/* {pharmacydetails?.faxnumber} */}
                                                {pharmacyContact?.faxnumber.number}
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
                                </Grid>
                            </Grid>
                            <Divider />
                            <Grid container>
                                <Grid item xs={12} sm={12}>
                                    <ListItemButton>
                                        <ListItemIcon>
                                            <PhonelinkRingTwoToneIcon sx={{ fontSize: '1.3rem' }} />
                                        </ListItemIcon>
                                        <ListItemText primary={<Typography variant="subtitle1">Email</Typography>} />
                                        <ListItemSecondaryAction>
                                            <Typography name="Email" variant="subtitle2">
                                                {/* {pharmacydetails?.email} */}
                                                {pharmacyContact?.email}
                                            </Typography>
                                        </ListItemSecondaryAction>
                                    </ListItemButton>
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
                                                {vaccineAdministered?.administrationdate}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Fips</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Fips" variant="subtitle2">
                                                {vaccineAdministered?.fips}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Locality</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Locality" variant="subtitle2">
                                                {vaccineAdministered?.locality}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Health District </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Healthdistrict" variant="subtitle2">
                                                {vaccineAdministered?.healthdistrict}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Faclity Type</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Faclitytype" variant="subtitle2">
                                                {vaccineAdministered?.faclitytype}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Manufacturer </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Manufacturer" variant="subtitle2">
                                                {vaccineAdministered?.manufacturer}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Dose Number </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Dosenumber" variant="subtitle2">
                                                {vaccineAdministered?.Dosenumber}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Vaccine Type </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Vaccinetype" variant="subtitle2">
                                                {vaccineAdministered?.vaccinetype}
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
                                                {knownAllergies?.allergen}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Reaction</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Reaction" variant="subtitle2">
                                                {knownAllergies?.reaction}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Is Severe</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Isserve" variant="subtitle2">
                                                {knownAllergies?.issevere}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">On set Date </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="onsetdate" variant="subtitle2">
                                                {knownAllergies?.onsetdate}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1"> Last Occurrence </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="lastoccurrence" variant="subtitle2">
                                                {knownAllergies?.lastoccurrence}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Notes </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="notes" variant="subtitle2">
                                                {knownAllergies?.notes}
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
                                                {currentMedications?.name}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Dosage</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="dosage" variant="subtitle2">
                                                {currentMedications?.dosage}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Instructions</Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Instructions" variant="subtitle2">
                                                {currentMedications?.Instructions}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Route Of Administration </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Routeofadministration" variant="subtitle2">
                                                {currentMedications?.Routeofadministration}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1"> Frequency </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Frequency" variant="subtitle2">
                                                {currentMedications?.Frequency}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Start Date </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="startdate" variant="subtitle2">
                                                {currentMedications?.stratdate}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Prescribing Doctor </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="prescribingdoctor" variant="subtitle2">
                                                {currentMedications?.prescribingdoctor}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Pharmacy </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="pharmacy" variant="subtitle2">
                                                 {currentMedications?.pharmacy}
                                            </Typography>
                                        </Grid>
                                    </Grid>
                                    <Grid container>
                                        <Grid item xs={12} sm={4}>
                                            <Typography variant="subtitle1">Is Prescription </Typography>
                                        </Grid>
                                        <Grid item xs={12} sm={8}>
                                            <Typography name="Isprescription" variant="subtitle2">
                                                {currentMedications?.Isprescription}
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
                                    <IconEdit
                                        stroke={1.5}
                                        size="20px"
                                        onClick={() => {
                                            navigate('/user/edit-profile'); // Replace '/edit-profile' with the desired URL of the edit-profile page
                                        }}
                                    />
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
                                                {communication?.language}
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
                                                {communication?.prefered}
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
                                                {communication?.Timezone}
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
                                                {demographics?.ethnicity}
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
                                                {demographics?.race}
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
                                                {demographics?.nationality}
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
                                               {demographics?.gender}
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
                                                {education?.degree}
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
                                                {education?.Instituation}
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
                                                {education?.startdate}
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
                                                {education?.completiondate}
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
                                                 {employeeIdentity?.organizationId}
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
                                                {employeeIdentity?.Id}
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
                                                {employeeIdentity?.employeeId}
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
                                                {employeeIdentity?.identityVerified}
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
                                                {employeeIdentity?.status}
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
