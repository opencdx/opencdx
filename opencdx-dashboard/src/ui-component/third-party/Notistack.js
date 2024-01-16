import PropTypes from 'prop-types';

// material-ui
import { styled } from '@mui/material/styles';

// third-party
import { SnackbarProvider } from 'notistack';

// project import
import { useSelector } from 'utils/store';

// assets
import { Info, Warning, Error } from '@mui/icons-material';

// custom styles
const StyledSnackbarProvider = styled(SnackbarProvider)(({ theme }) => ({
    '&.notistack-MuiContent-default': {
        backgroundColor: theme.palette.primary.main
    },
    '&.notistack-MuiContent-error': {
        backgroundColor: theme.palette.error.main
    },
    '&.notistack-MuiContent-success': {
        backgroundColor: theme.palette.success.main
    },
    '&.notistack-MuiContent-info': {
        backgroundColor: theme.palette.info.main
    },
    '&.notistack-MuiContent-warning': {
        backgroundColor: theme.palette.warning.main
    }
}));

// ===========================|| SNACKBAR - NOTISTACK ||=========================== //

const Notistack = ({ children }) => {
    const snackbar = useSelector((state) => state.snackbar);
    const iconSX = { marginRight: 8, fontSize: '1.15rem' };

    return (
        <StyledSnackbarProvider
            maxSnack={snackbar.maxStack}
            dense={snackbar.dense}
            iconVariant={
                snackbar.iconVariant === 'useemojis'
                    ? {
                          success: <Info style={iconSX} />,
                          error: <Error style={iconSX} />,
                          warning: <Warning style={iconSX} />,
                          info: <Info style={iconSX} />
                      }
                    : undefined
            }
            // eslint-disable-next-line
            hideIconVariant={snackbar.iconVariant === 'hide' ? true : false}
        >
            {children}
        </StyledSnackbarProvider>
    );
};

Notistack.propTypes = {
    children: PropTypes.node
};

export default Notistack;
