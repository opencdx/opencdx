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
        }
    ]
};

export default documentation;
