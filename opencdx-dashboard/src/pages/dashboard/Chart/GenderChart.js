import PropTypes from 'prop-types';

// material-ui

// third-party
import Chart from 'react-apexcharts';

// project imports
import MainCard from 'ui-component/cards/MainCard';

// assets

// =========================|| CONVERSIONS CHART CARD ||========================= //
// ==============================|| WIDGET - CONVERSION CHART ||============================== //


// ==============================|| WIDGET - Gender CHART ||============================== //
const GenderChart = ({ gender }) => {
    console.log('GenderChart:', gender);
    const chartData = {
        height: 228,
        type: 'donut',
        options: {
            chart: {
                id: 'gender-chart'
            },
            dataLabels: {
                enabled: false
            },
            labels: ['Male', 'Female'],
            legend: {
                show: true,
                position: 'bottom',
                fontFamily: 'inherit',
                labels: {
                    colors: 'inherit'
                },
                itemMargin: {
                    horizontal: 10,
                    vertical: 10
                }
            }
        },
        series: [gender[0],gender[1]]
    };
    
    return (
        <MainCard title="Gender ">
            <Chart {...chartData} />
        </MainCard>
    );
};

GenderChart.propTypes = {
    gender: PropTypes.array
};

export default GenderChart;
