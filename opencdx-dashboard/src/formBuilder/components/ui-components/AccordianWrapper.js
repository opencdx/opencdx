import React from 'react';
import PropTypes from 'prop-types';
import Accordion from '@mui/material/Accordion';
import AccordionSummary from '@mui/material/AccordionSummary';
import AccordionDetails from '@mui/material/AccordionDetails';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import Typography from '@mui/material/Typography';

const AccordianWrapper = React.forwardRef(({ title, children }, ref) => {
    return (
        <Accordion ref={ref}>
            <AccordionSummary
                sx={{
                    backgroundColor: 'lightgray',
                    border: '1px solid lightgray',
                    minHeight: '20px',
                    '&.Mui-expanded': {
                        minHeight: '48px'
                    },
                    '&:hover': {
                        backgroundColor: 'lightgray',
                        cursor: 'pointer'
                    }
                }}
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <Typography variant="h5" sx={{ fontWeight: 500 }}>
                    {title}
                </Typography>
            </AccordionSummary>
            <AccordionDetails
                sx={{
                    backgroundColor: '#f5f5f5',
                    border: '1px solid lightgray',
                    minHeight: '20px',
                    '&.Mui-expanded': {
                        minHeight: '48px'
                    }
                }}
            >
                {children}
            </AccordionDetails>
        </Accordion>
    );
});
AccordianWrapper.propTypes = {
    title: PropTypes.string,
    children: PropTypes.node
};
export { AccordianWrapper };
