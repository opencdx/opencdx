// material-ui

// project imports
import MainCard from 'ui-component/cards/MainCard';

// ==============================|| SAMPLE PAGE ||============================== //

const Iframe = ({href,name}) => (
    <MainCard >
        <iframe  src={href}
        width="100%"
        height="500px"
        allowFullScreen
        name={name}
        title={name}
       />
    </MainCard>
);

export default Iframe;
