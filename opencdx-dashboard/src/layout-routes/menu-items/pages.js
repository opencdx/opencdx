// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { Share, Monitor, DeviceHub } from '@mui/icons-material';

// constant

// ==============================|| EXTRA PAGES MENU ITEMS ||============================== //

const pages = {
    id: 'pages',
    title: <FormattedMessage id="pages" />,
    type: 'group',
    children: [
        {
            id: 'template',
            title: <FormattedMessage id="template" />,
            type: 'collapse',
            icon: Monitor,
            children: [
                {
                    id: 'email',
                    title: <FormattedMessage id="email" />,
                    type: 'item',
                    icon: DeviceHub,
                    url: '/pages/email'
                },

                {
                    id: 'sms',
                    title: <FormattedMessage id="sms" />,
                    type: 'item',
                    icon: Share,
                    url: '/pages/sms'
                }
            ]
        }
    ]
};

export default pages;
