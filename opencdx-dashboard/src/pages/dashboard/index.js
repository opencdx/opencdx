// material-ui

// project imports
import DataCard from './DataCard';
import AccountCircleTwoTone from '@mui/icons-material/AccountCircleTwoTone';
import EmojiEmotionsTwoToneIcon from '@mui/icons-material/EmojiEmotionsTwoTone';
import RemoveRedEyeTwoToneIcon from '@mui/icons-material/RemoveRedEyeTwoTone';
import MonetizationOnTwoToneIcon from '@mui/icons-material/MonetizationOnTwoTone';
import ShoppingCartTwoToneIcon from '@mui/icons-material/ShoppingCartTwoTone';
import AccountBalanceWalletTwoToneIcon from '@mui/icons-material/AccountBalanceWalletTwoTone';

import { Grid } from '@mui/material';
import { gridSpacing } from 'utils/store/constant';
import { useTheme } from '@mui/material/styles';
import { useEffect, useState } from 'react';
import { data } from './Chart/data';
import GenderChart from './Chart/GenderChart';
// import RaceChart from './Chart/Race';

//==============================|| SAMPLE PAGE ||============================== //

const Dashboard = () => {
    const [gender, setGender] = useState([0, 0]);
    // const [race, setRace] = useState([0, 0, 0]);

    useEffect(() => {
        const filteredData = data
            .map((item) => {
                return {
                    ...item,
                    gender: item.gender === 'F' ? 'Female' : item.gender === 'M' ? 'Male' : item.gender
                };
            })
            .filter((item) => item.gender === 'Male' || item.gender === 'Female');

        const maleCount = filteredData.filter((item) => item.gender === 'Male').length;
        const femaleCount = filteredData.filter((item) => item.gender === 'Female').length;
        setGender([maleCount, femaleCount]);
        // const raceCounts ={};
        // data.forEach(item => {
        //     const race = item.race;
        //     if (raceCounts[race]) {
        //         raceCounts[race]++;
        //     } else {
        //         raceCounts[race] = 1;
        //     }
        // });

        // console.log(raceCounts);

        // setRace([raceCounts.hawaiian,raceCounts.black,raceCounts.white]);
    }, []);

    const theme = useTheme();
    return (
        <Grid>
            <Grid container spacing={gridSpacing} alignItems="center">
                <Grid item xs={12} lg={2}>
                    <DataCard primary="Total users" secondary="24" color={theme.palette.primary.main} iconPrimary={AccountCircleTwoTone} />
                </Grid>
                <Grid item xs={12} lg={2}>
                    <DataCard
                        primary="Active users"
                        secondary="4"
                        color={theme.palette.orange.dark}
                        iconPrimary={EmojiEmotionsTwoToneIcon}
                    />
                </Grid>
                <Grid item xs={12} lg={2}>
                    <DataCard
                        primary="Inactive users"
                        secondary="20"
                        color={theme.palette.warning.dark}
                        iconPrimary={RemoveRedEyeTwoToneIcon}
                    />
                </Grid>
                <Grid item xs={12} lg={2}>
                    <DataCard
                        primary="Test types"
                        secondary="2"
                        color={theme.palette.secondary.main}
                        iconPrimary={MonetizationOnTwoToneIcon}
                    />
                </Grid>
                <Grid item xs={12} lg={2}>
                    <DataCard
                        primary="Organizations"
                        secondary="2"
                        color={theme.palette.success.main}
                        iconPrimary={ShoppingCartTwoToneIcon}
                    />
                </Grid>
                <Grid item xs={12} lg={2}>
                    <DataCard
                        primary="Transactions"
                        secondary="2"
                        color={theme.palette.orange.main}
                        iconPrimary={AccountBalanceWalletTwoToneIcon}
                    />
                </Grid>
            </Grid>
            <Grid
                container
                alignItems="center"
                justifyContent="space-between"
                sx={{
                    mt: 3,
                    mb: 3
                }}
            >
                <GenderChart gender={gender} />
                {/* <RaceChart race={race} /> */}
            </Grid>
        </Grid>
    );
};

export default Dashboard;
