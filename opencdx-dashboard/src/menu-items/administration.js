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

const documentation = {
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
            url: 'https://localhost:8761/',

        },
        {
            id: 'admin',
            title: <FormattedMessage id="admin" />,
            type: 'item',
            target: true,
            external: true,
            url: 'https://localhost:8861/admin/wallboard',

        },
    ]
};

export default documentation;
