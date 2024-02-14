import { FormattedMessage } from 'react-intl';
import FactCheckIcon from '@mui/icons-material/FactCheck';
import VerifiedUserIcon from '@mui/icons-material/VerifiedUser';
import LinkIcon from '@mui/icons-material/Link';

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
            icon: VerifiedUserIcon,
            children: [
                {
                    id: 'audit-log',
                    title: <FormattedMessage id="audit-log" />,
                    icon: FactCheckIcon,
                    type: 'item',
                    url: '/pages/audit-log'
                }
            ]
        },
        {
            id: 'external',
            title: <FormattedMessage id="external-interfaces" />,
            type: 'collapse',
            icon: LinkIcon,
            children: [
                {
                    id: 'external-interfaces',
                    title: <FormattedMessage id="external-interfaces" />,
                    type: 'item',
                    icon: LinkIcon,
                    url: '/pages/external-interfaces'
                }
            ]
        }
    ]
};

export default reports;
