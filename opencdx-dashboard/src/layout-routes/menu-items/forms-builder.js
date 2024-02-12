// third-party
import { FormattedMessage } from 'react-intl';
import ArticleIcon from '@mui/icons-material/Article';
import PostAddIcon from '@mui/icons-material/PostAdd';
import PageviewIcon from '@mui/icons-material/Pageview';

// ==============================|| DOCUMENTATION MENU ITEMS ||============================== //

const formsbuilder = {
    id: 'forms-builder',
    title: <FormattedMessage id="forms-builder" />,
    type: 'group',
    children: [
        {
            id: 'forms-builder',
            title: <FormattedMessage id="forms-builder" />,
            type: 'collapse',
            icon: ArticleIcon,
            children: [
                {
                    id: 'forms-designer',
                    title: <FormattedMessage id="forms-designer" />,
                    type: 'item',
                    icon: PostAddIcon,
                    target: true,
                    url: '/form-builder'
                },
                {
                    id: 'forms-viewer',
                    title: <FormattedMessage id="forms-viewer" />,
                    type: 'item',
                    icon: PageviewIcon,
                    url: '/pages/form-viewer'
                }
            ]
        }
    ]
};

export default formsbuilder;
