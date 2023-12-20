// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconHelp, IconSitemap } from '@tabler/icons-react';

// constant
const icons = {
    IconHelp,
    IconSitemap
};

// ==============================|| SAMPLE PAGE & DOCUMENTATION MENU ITEMS ||============================== //

const other = {
    id: 'sample-docs-roadmap',
    icon: icons.IconHelp,
    type: 'group',
    children: [
        {
            id: 'discovery',
            title: <FormattedMessage id="discovery" />,
            type: 'item',
            target: true,
            external: true,
            url: 'https://localhost:8761/'
        },
        {
            id: 'admin',
            title: <FormattedMessage id="admin" />,
            type: 'item',
            target: true,
            external: true,
            url: 'https://localhost:8861/admin/wallboard'
        },

        {
            id: 'java',
            title: <FormattedMessage id="java" />,
            type: 'item',
            url: '/pages/java'
        },
        {
            id: 'proto',
            title: <FormattedMessage id="proto" />,
            type: 'item',
            url: '/pages/proto'
        },
        {
            id: 'login',
            title: <FormattedMessage id="login" />,
            type: 'item',
            target: true,
            url: '/login'
        },
        {
            id: 'register',
            title: <FormattedMessage id="register" />,
            type: 'item',
            target: true,
            url: '/register'
        }
    ]
};

export default other;
