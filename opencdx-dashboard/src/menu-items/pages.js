// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconKey, IconBug } from '@tabler/icons-react';

// constant
const icons = { IconBug, IconKey };

// ==============================|| EXTRA PAGES MENU ITEMS ||============================== //

const pages = {
    id: 'pages',
    title: <FormattedMessage id="pages" />,
    icon: icons.IconKey,
    type: 'group',
    children: [
        {
            id: 'nats',
            title: <FormattedMessage id="nats" />,
            type: 'collapse',
            children: [
                {
                    id: 'general',
                    title: <FormattedMessage id="general" />,
                    type: 'item',
                    url: '/pages/general'
                },

                {
                    id: 'jet',
                    title: <FormattedMessage id="jet" />,
                    type: 'item',
                    url: '/pages/jet'
                },

                {
                    id: 'connections',
                    title: <FormattedMessage id="connections" />,
                    type: 'item',
                    url: '/pages/connections'
                },

                {
                    id: 'accounts',
                    title: <FormattedMessage id="accounts" />,
                    type: 'item',
                    url: '/pages/accounts'
                },

                {
                    id: 'accounts-stats',
                    title: <FormattedMessage id="accounts-stats" />,
                    type: 'item',
                    url: '/pages/accounts-stats'
                },

                {
                    id: 'subscriptions',
                    title: <FormattedMessage id="subscriptions" />,
                    type: 'item',
                    url: '/pages/subscriptions'
                },

                {
                    id: 'routes',
                    title: <FormattedMessage id="routes" />,
                    type: 'item',
                    url: '/pages/routes'
                },

                {
                    id: 'leaf-nodes',
                    title: <FormattedMessage id="leaf-nodes" />,
                    type: 'item',
                    url: '/pages/leaf-nodes'
                },

                {
                    id: 'gateways',
                    title: <FormattedMessage id="gateways" />,
                    type: 'item',
                    url: '/pages/gateways'
                },

                {
                    id: 'health-probe',
                    title: <FormattedMessage id="health-probe" />,
                    type: 'item',
                    url: '/pages/health-probe'
                }
            ]
        },
        {
            id: 'template',
            title: <FormattedMessage id="template" />,
            type: 'collapse',
            children: [
                {
                    id: 'email',
                    title: <FormattedMessage id="email" />,
                    type: 'item',
                    url: '/pages/email'
                },

                {
                    id: 'sms',
                    title: <FormattedMessage id="sms" />,
                    type: 'item',
                    url: '/pages/sms'
                }
            ]
        },
        {
            id: 'profile',
            title: <FormattedMessage id="profile" />,
            type: 'collapse',
            children: [
                {
                    id: 'view',
                    title: <FormattedMessage id="view-profile" />,
                    type: 'item',
                    url: '/user/view-profile'
                },

                {
                    id: 'edit',
                    title: <FormattedMessage id="edit-profile" />,
                    type: 'item',
                    url: '/user/edit-profile'
                }
            ]
        }
    ]
};

export default pages;
