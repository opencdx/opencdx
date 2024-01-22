import PropTypes from 'prop-types';
import { useState, useEffect, useCallback } from 'react';
import Chart from 'react-apexcharts';
import MainCard from 'ui-component/cards/MainCard';
import axios from 'axios';

// ==============================|| WIDGET - Race CHART ||============================== //

const RaceChart = () => {
    const [race, setRace] = useState([0, 0, 0, 0, 0, 0]);


    const fetchData = useCallback(async () => {
        try {
            const response = await axios.post('http://localhost:8632/graphql', {
                query: `{
                    getPatients {
                        race
                        
                    }
                }`
            });

            let blackCount = 0;
            let whiteCount = 0;
            let hispanicCount = 0;
            let asianCount = 0;


            response.data.data?.getPatients.forEach((item) => {
                if (item.race === 'black') {
                    blackCount++;
                } else if (item.race === 'white') {
                    whiteCount++;
                } else if (item.race === 'Hispanic') {
                    hispanicCount++;
                }
                else if (item.race === 'Asian') {
                    asianCount++;
                }
            });

            setRace([blackCount, whiteCount, hispanicCount, asianCount]);
        } catch (error) {
            console.error(error);
        }
    }, []);

    useEffect(() => {
        fetchData();
    }, [fetchData]);

    const series = [{
        name: 'Race',
        data: race
    }]
    const options =
    {
        chart: {
            type: 'bar',
            height: 350
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: '55%',
                endingShape: 'rounded'
            },
        },
        dataLabels: {
            enabled: false
        },
        stroke: {
            show: true,
            width: 2,
            colors: ['transparent']
        },
        xaxis: {
            categories: ['American Indian ', 'Asian', 'Black or African American', 'Hispanic or Latino', ' Hawaiian ', 'White'],
        },
        yaxis: {
            title: {
                text: ''
            }
        },
        fill: {
            opacity: 1
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return val
                }
            }
        }
    }
    return (
        <MainCard title="Race ">
            <Chart series={series} options={options} type="bar" height={350} />
        </MainCard>
    );
};

RaceChart.propTypes = {
    gender: PropTypes.array
};

export default RaceChart;
