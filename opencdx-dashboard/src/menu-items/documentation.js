// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { IconBrandNetbeans, IconPropeller } from '@tabler/icons-react';

// ==============================|| DOCUMENTATION MENU ITEMS ||============================== //

const documentation = {
    id: 'documentation',
    title: <FormattedMessage id="documentation" />,
    type: 'group',
    children: [
        {
            id: 'java',
            title: <FormattedMessage id="java" />,
            type: 'item',
            icon: IconBrandNetbeans,
            url: '/pages/java'
        },
        {
            id: 'proto',
            title: <FormattedMessage id="proto" />,
            type: 'item',
            icon: IconPropeller,
            url: '/pages/proto'
        },
    ]
};

export default documentation;
