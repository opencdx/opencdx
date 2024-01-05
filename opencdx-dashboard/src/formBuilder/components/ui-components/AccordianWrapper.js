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
                    backgroundColor: 'lightgray'
                }}
                expandIcon={<ExpandMoreIcon />}
                aria-controls="panel1a-content"
                id="panel1a-header"
            >
                <Typography>{title}</Typography>
            </AccordionSummary>
            <AccordionDetails>{children}</AccordionDetails>
        </Accordion>
    );
});
AccordianWrapper.propTypes = {
    title: PropTypes.string,
    children: PropTypes.node
};
export { AccordianWrapper };
