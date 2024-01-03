// third-party
import { FormattedMessage } from 'react-intl';

// assets
// import { IconBrandNetbeans, IconPropeller } from '@tabler/icons-react';

// ==============================|| DOCUMENTATION MENU ITEMS ||============================== //

const reports = {
    id: 'reports',
    title: <FormattedMessage id="reports" />,
    type: 'group',
    children: [
        {
            id: 'audit-records',
            title: <FormattedMessage id="audit-records" />,
            type: 'collapse',
            children: [
                {
                    id: 'audit-log',
                    title: <FormattedMessage id="audit-log" />,
                    type: 'item',
                    url: '/pages/audit-log'
                }
            ]
        },
        {
            id: 'external',
            title: <FormattedMessage id="external-interfaces" />,
            type: 'collapse',
            children: [
                {
                    id: 'external-interfaces',
                    title: <FormattedMessage id="external-interfaces" />,
                    type: 'item',
                    url: '/pages/external-interfaces'
                }
            ]
        }
    ]
};

export default reports;
