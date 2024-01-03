// This is example of menu item without group for horizontal layout. There will be no children.

// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconHome2 } from '@tabler/icons-react';

// ==============================|| MENU ITEMS - SAMPLE PAGE ||============================== //

const samplePage = {
    id: 'dashboard',
    title: <FormattedMessage id="dashboard" />,
    icon: IconHome2,
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
