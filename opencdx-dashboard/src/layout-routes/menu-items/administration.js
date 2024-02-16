// third-party
import { FormattedMessage } from 'react-intl';
import { AdminPanelSettings, StackedBarChart } from '@mui/icons-material';
import AssessmentIcon from '@mui/icons-material/Assessment';
import TravelExploreIcon from '@mui/icons-material/TravelExplore';

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
            icon: TravelExploreIcon,
            target: true,
            external: true,
            url: 'https://localhost:8761/'
        },
        {
            id: 'admin',
            title: <FormattedMessage id="admin" />,
            type: 'item',
            icon: AdminPanelSettings,
            target: true,
            external: true,
            url: '/pages/admin'
        },
        {
            id: 'dependency',
            title: <FormattedMessage id="dependency" />,
            type: 'item',
            icon: AssessmentIcon,
            target: true,
            external: true,
            url: '/pages/dependency'
        },
        {
            id: 'nats',
            title: <FormattedMessage id="nats" />,
            type: 'collapse',
            icon: StackedBarChart,
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
