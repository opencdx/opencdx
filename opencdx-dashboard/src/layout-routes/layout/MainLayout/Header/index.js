// material-ui
import { useTheme } from '@mui/material/styles';
import { Avatar, Box, useMediaQuery } from '@mui/material';

// project imports
import LAYOUT_CONST from 'utils/constant';
import useConfig from 'utils/hooks/useConfig';
import LogoSection from '../LogoSection';
import FullScreenSection from './FullScreenSection';

import { useDispatch, useSelector } from 'utils/store';
import { openDrawer } from 'utils/store/slices/menu';
import IconMenu2 from '@mui/icons-material/Menu';

// assets

// ==============================|| MAIN NAVBAR / HEADER ||============================== //

const Header = () => {
    const theme = useTheme();

    const dispatch = useDispatch();
    const { drawerOpen } = useSelector((state) => state.menu);

    const matchDownMd = useMediaQuery(theme.breakpoints.down('md'));
    const { layout } = useConfig();

    return (
        <>
            {/* logo & toggler button */}
            <Box
                sx={{
                    width: 228,
                    display: 'flex',
                    [theme.breakpoints.down('md')]: {
                        width: 'auto'
                    }
                }}
            >
                <Box component="span" sx={{ display: { xs: 'none', md: 'block' }, flexGrow: 1 }}>
                    <LogoSection />
                </Box>

                {layout === LAYOUT_CONST.VERTICAL_LAYOUT || (layout === LAYOUT_CONST.HORIZONTAL_LAYOUT && matchDownMd) ? (
                    <Avatar
                        variant="rounded"
                        sx={{
                            ...theme.typography.commonAvatar,
                            ...theme.typography.mediumAvatar,
                            overflow: 'hidden',
                            transition: 'all .2s ease-in-out',
                            background: theme.palette.mode === 'dark' ? theme.palette.dark.main : theme.palette.primary.light,
                            color: theme.palette.mode === 'dark' ? theme.palette.primary.main : theme.palette.primary.dark,
                            '&:hover': {
                                background: theme.palette.mode === 'dark' ? theme.palette.primary.main : theme.palette.primary.dark,
                                color: theme.palette.mode === 'dark' ? theme.palette.primary.light : theme.palette.primary.light
                            }
                        }}
                        onClick={() => dispatch(openDrawer(!drawerOpen))}
                        color="inherit"
                    >
                        <IconMenu2 stroke={1.5} size="20px" />
                    </Avatar>
                ) : null}
            </Box>

            {/* header search */}
            <Box sx={{ flexGrow: 1 }} />
            <Box sx={{ flexGrow: 1 }} />

            {/* full sceen toggler */}
            <Box sx={{ display: { xs: 'none', lg: 'block' } }}>
                <FullScreenSection />
            </Box>
        </>
    );
};

export default Header;
