// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconTopologyStar3, IconUserCircle, IconTarget } from '@tabler/icons-react';

// ==============================|| SAMPLE PAGE & DOCUMENTATION MENU ITEMS ||============================== //

const documentation = {
    id: 'administration',
    title: <FormattedMessage id="administration" />,
    type: 'group',
    children: [
        {
            id: 'discovery',
            title: <FormattedMessage id="discovery" />,
            type: 'item',
            icon: IconTarget,
            target: true,
            external: true,
            url: 'https://localhost:8761/'
        },
        {
            id: 'admin',
            title: <FormattedMessage id="admin" />,
            type: 'item',
            icon: IconUserCircle,
            target: true,
            external: true,
            url:'/pages/admin'
        },
        {
            id: 'nats',
            title: <FormattedMessage id="nats" />,
            type: 'collapse',
            icon: IconTopologyStar3,
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
        }
    ]
};

export default documentation;
