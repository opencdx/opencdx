// material-ui
import { useTheme } from '@mui/material/styles';
import { Grid } from '@mui/material';

// project-import
import MainCard from 'ui-component/cards/MainCard'; 

import ClustersMap from './clusters-map';
import Heatmap from './heatmap';

const MAPBOX_THEMES = {
    light: 'mapbox://styles/mapbox/light-v10',
    dark: 'mapbox://styles/mapbox/dark-v10',
    streets: 'mapbox://styles/mapbox/streets-v11',
    outdoors: 'mapbox://styles/mapbox/outdoors-v11',
    satellite: 'mapbox://styles/mapbox/satellite-v9',
    satelliteStreets: 'mapbox://styles/mapbox/satellite-streets-v11'
};

const mapConfiguration = {
    mapboxAccessToken: process.env.REACT_APP_MAPBOX_ACCESS_TOKEN,
    minZoom: 1
};


// ==============================|| MAP ||============================== //

const Map = () => {
    const theme = useTheme();

    return (
        <Grid container spacing={3}>
            
           
          
            
            <Grid item xs={12}>
                <MainCard title="Clusters">
             
                    <Grid sx={{ zIndex: 0, height: 576, overflow: 'hidden', position: 'relative', borderRadius: 4 ,

                    }}>
                        <ClustersMap
                            {...mapConfiguration}
                            mapStyle={theme.palette.mode === 'dark' ? MAPBOX_THEMES.dark : MAPBOX_THEMES.light}
                        />
                    </Grid>
                </MainCard>
            </Grid>
           
           
           
            <Grid item xs={12} md={12}>
                <MainCard title="Heatmap">
                    <Grid sx={{ zIndex: 0, height: 576, overflow: 'hidden', position: 'relative', borderRadius: 4 ,
                    
                    }}>
                        <Heatmap
                            {...mapConfiguration}
                            mapStyle={theme.palette.mode === 'dark' ? MAPBOX_THEMES.dark : MAPBOX_THEMES.light}
                        />
                    </Grid>
                </MainCard>
            </Grid>
           
        </Grid>
    );
};

export default Map;
