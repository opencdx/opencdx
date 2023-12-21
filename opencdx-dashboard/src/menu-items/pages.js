// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconLocationShare, IconDeviceMobileMessage,IconDeviceHeartMonitor } from '@tabler/icons-react';

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
            icon: IconDeviceHeartMonitor,
            children: [
                {
                    id: 'email',
                    title: <FormattedMessage id="email" />,
                    type: 'item',
                    icon: IconDeviceMobileMessage,
                    url: '/pages/email'
                },

                {
                    id: 'sms',
                    title: <FormattedMessage id="sms" />,
                    type: 'item',
                    icon: IconLocationShare,
                    url: '/pages/sms'
                }
            ]
        },
        
    ]
};

export default pages;
