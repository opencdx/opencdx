// third-party
import { FormattedMessage } from 'react-intl';

// assets
import { Javascript, Image } from '@mui/icons-material';

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
            icon: Javascript,
            url: '/pages/java'
        },
        {
            id: 'proto',
            title: <FormattedMessage id="proto" />,
            type: 'item',
            icon: Image,
            url: '/pages/proto'
        }
    ]
};

export default documentation;
