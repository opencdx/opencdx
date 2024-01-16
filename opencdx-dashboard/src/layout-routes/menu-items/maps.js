// This is example of menu item without group for horizontal layout. There will be no children.

// third-party
import { FormattedMessage } from 'react-intl';

// assets
import IconMap from '@mui/icons-material/Map';

// ==============================|| MENU ITEMS - SAMPLE PAGE ||============================== //

const maps = {
    id: 'maps',
    title: <FormattedMessage id="maps" />,
    icon: IconMap,
    type: 'group',
    url: '/dashboard/maps'
};

export default maps;
