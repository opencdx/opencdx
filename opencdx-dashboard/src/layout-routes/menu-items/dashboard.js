// This is example of menu item without group for horizontal layout. There will be no children.

// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { Home } from '@mui/icons-material';

// ==============================|| MENU ITEMS - SAMPLE PAGE ||============================== //

const samplePage = {
    id: 'dashboard',
    title: <FormattedMessage id="dashboard" />,
    icon: Home,
    type: 'group',
    url: '/dashboard',
    children: [
        {
            id: 'dashboard-maps',
            title: <FormattedMessage id="maps" />,
            type: 'item',
            url: '/dashboard/maps',
            breadcrumbs: false
        }
    ]
};

export default samplePage;
