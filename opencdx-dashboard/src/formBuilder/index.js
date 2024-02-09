import { JsonView, allExpanded, defaultStyles } from 'react-json-view-lite';
import PropTypes from 'prop-types';
import 'react-json-view-lite/dist/index.css';
import * as React from 'react';
import {
    Box,
    Drawer,
    CssBaseline,
    Toolbar,
    List,
    Typography,
    Divider,
    ListItem,
    ListItemButton,
    ListItemText,
    Button,
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    IconButton
} from '@mui/material';
import MuiAppBar from '@mui/material/AppBar';
import { styled, useTheme } from '@mui/material/styles';

import ChevronLeftIcon from '@mui/icons-material/ChevronLeft';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';

import CloudUploadIcon from '@mui/icons-material/CloudUpload';
import CloseIcon from '@mui/icons-material/Close';
import useLocalStorage from './utils/useLocalStorage';

/* start - anf statement type */
import StatementTypesReport from './components/ui-components/StatementTypesReport';
import MainWrapper from './components/ui-components/MainWrapper';
import FullScreenSection from './components/ui-components/FullScreen';
import { Grid } from '@mui/material';
import MenuIcon from '@mui/icons-material/Menu';
import GetAppIcon from '@mui/icons-material/GetApp';

import './custom.css';

/* end - anf statement type */

const BootstrapDialog = styled(Dialog)(({ theme }) => ({
    '& .MuiDialogContent-root': {
        padding: theme.spacing(2)
    },
    '& .MuiDialogActions-root': {
        padding: theme.spacing(1)
    }
}));

const drawerWidth = 240;

const Main = styled('main', { shouldForwardProp: (prop) => prop !== 'open' })(({ theme, open }) => ({
    flexGrow: 1,
    padding: theme.spacing(3),
    transition: theme.transitions.create('margin', {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen
    }),
    marginLeft: `-${drawerWidth}px`,
    ...(open && {
        transition: theme.transitions.create('margin', {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen
        }),
        marginLeft: 0
    })
}));

const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== 'open'
})(({ theme, open }) => ({
    transition: theme.transitions.create(['margin', 'width'], {
        easing: theme.transitions.easing.sharp,
        duration: theme.transitions.duration.leavingScreen
    }),
    ...(open && {
        width: `calc(100% - ${drawerWidth}px)`,
        marginLeft: `${drawerWidth}px`,
        transition: theme.transitions.create(['margin', 'width'], {
            easing: theme.transitions.easing.easeOut,
            duration: theme.transitions.duration.enteringScreen
        })
    })
}));

const DrawerHeader = styled('div')(({ theme }) => ({
    display: 'flex',
    alignItems: 'center',
    padding: theme.spacing(0, 1),
    // necessary for content to be below app bar
    ...theme.mixins.toolbar,
    justifyContent: 'flex-end'
}));

const VisuallyHiddenInput = styled('input')({
    clip: 'rect(0 0 0 0)',
    clipPath: 'inset(50%)',
    height: 1,
    overflow: 'hidden',
    position: 'absolute',
    bottom: 0,
    left: 0,
    whiteSpace: 'nowrap',
    width: 1
});

const FormBuilder = () => {
    const theme = useTheme();
    const [open, setOpen] = React.useState(true);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };
    const [openDialog, setOpenDialog] = React.useState(false);
    const [openAnfDialog, setOpenAnfDialog] = React.useState(false);
    const [files, setFiles] = useLocalStorage('anf-form');
    const [uploadJson, setUploadedJson] = useLocalStorage('uploaded-form');

    const handleChange = (e) => {
        const fileReader = new FileReader();
        fileReader.readAsText(e.target.files[0], 'UTF-8');
        fileReader.onload = (e) => {
            setFiles(JSON.parse(e.target.result));
            setUploadedJson(JSON.parse(e.target.result));
        };
    };

    const handleClickOpen = () => {
        setOpenDialog(true);
    };
    const handleClose = () => {
        setOpenDialog(false);
    };
    const handleClickOpenAnfDialog = () => {
        setOpenAnfDialog(true);
    };
    const handleCloseAnfDialog = () => {
        setOpenAnfDialog(false);
    };
    const handlePreviewDownload = () => {
        handleDownload(uploadJson, 'upload-form.json');
    };
    const handleAnfDownload = () => {
        handleDownload(files, 'anf-form.json');
    };
    const handleDownload = (uploadJson, fileName) => {
        const data = JSON.stringify(uploadJson);
        const blob = new Blob([data], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const link = document.createElement('a');
        link.href = url;
        link.download = fileName;
        link.click();
        URL.revokeObjectURL(url);
    };
    const DialogWrapper = ({ open, handleClose, title, children, handleDownload }) => {
        return (
            <BootstrapDialog onClose={handleClose} aria-labelledby="customized-dialog-title" open={open}>
                <DialogTitle id="customized-dialog-title" onClose={handleClose}>
                    {title}
                    <IconButton
                        aria-label="close"
                        onClick={handleClose}
                        sx={{
                            position: 'absolute',
                            right: 8,
                            top: 8,
                            color: (theme) => theme.palette.grey[500]
                        }}
                    >
                        <CloseIcon />
                    </IconButton>
                </DialogTitle>
                <DialogContent dividers>{children}</DialogContent>
                <DialogActions>
                    <Button onClick={() => handleDownload()} startIcon={<GetAppIcon />}>
                        Download
                    </Button>
                    <Button autoFocus onClick={handleClose}>
                        Close
                    </Button>
                </DialogActions>
            </BootstrapDialog>
        );
    };

    return (
        <Box sx={{ display: 'flex' }}>
            <CssBaseline />
            <AppBar position="fixed" open={open} style={{ backgroundColor: 'white' }}>
                <Toolbar style={{ display: 'flex', justifyContent: 'space-between' }}>
                    <IconButton
                        color="inherit"
                        aria-label="open drawer"
                        onClick={handleDrawerOpen}
                        edge="start"
                        sx={{ mr: 2, ...(open && { display: 'none' }) }}
                    >
                        <MenuIcon />
                    </IconButton>
                    <Typography variant="h3" component="div">
                        ANF Statement
                    </Typography>
                    <Grid item style={{ display: 'flex', justifyContent: 'space-between' }}>
                        <Box sx={{ display: { xs: 'none', lg: 'block' } }}>
                            <FullScreenSection />
                        </Box>
                    </Grid>
                </Toolbar>
            </AppBar>
            <Drawer
                sx={{
                    width: drawerWidth,
                    flexShrink: 0,
                    '& .MuiDrawer-paper': {
                        width: drawerWidth,
                        boxSizing: 'border-box'
                    }
                }}
                variant="persistent"
                anchor="left"
                open={open}
            >
                <DrawerHeader>
                    <Button onChange={handleChange} component="label" variant="contained" startIcon={<CloudUploadIcon />}>
                        Upload file
                        <VisuallyHiddenInput type="file" />
                    </Button>
                    <IconButton onClick={handleDrawerClose}>
                        {theme.direction === 'ltr' ? <ChevronLeftIcon /> : <ChevronRightIcon />}
                    </IconButton>
                </DrawerHeader>

                <Divider />
                <List>
                    {files &&
                        files?.item.map(({ linkId, text }) => (
                            <ListItem key={text} disablePadding>
                                <ListItemButton>
                                    <ListItemText primary={linkId + ' - ' + text} />
                                </ListItemButton>
                            </ListItem>
                        ))}
                </List>
            </Drawer>
            <Main open={open}>
                <DrawerHeader />
                <Box component="main" sx={{ flexGrow: 1, bgcolor: 'background.default' }}>
                    {uploadJson && uploadJson.item && (
                        <Button sx={{ m: 1 }} variant="contained" onClick={handleClickOpen}>
                            User Form JSON
                        </Button>
                    )}
                    {files && files.item && (
                        <Button sx={{ m: 1 }} variant="contained" onClick={handleClickOpenAnfDialog}>
                            ANF Statement JSON
                        </Button>
                    )}

                    <DialogWrapper open={openDialog} handleClose={handleClose} title="Preview JSON" handleDownload={handlePreviewDownload}>
                        <JsonView data={uploadJson} shouldExpandNode={allExpanded} style={defaultStyles} />
                    </DialogWrapper>
                    <DialogWrapper
                        open={openAnfDialog}
                        handleClose={handleCloseAnfDialog}
                        title="ANF Statement"
                        handleDownload={handleAnfDownload}
                    >
                        <JsonView data={files} shouldExpandNode={allExpanded} style={defaultStyles} />
                    </DialogWrapper>
                    <StatementTypesReport />

                    {files && files.item && <MainWrapper key={files} uploadedFile={files} />}
                </Box>
            </Main>
        </Box>
    );
};
FormBuilder.propTypes = {
    children: PropTypes.node,
    handleClose: PropTypes.func,
    handleDownload: PropTypes.func,
    open: PropTypes.bool,
    title: PropTypes.string
};

export default FormBuilder;
