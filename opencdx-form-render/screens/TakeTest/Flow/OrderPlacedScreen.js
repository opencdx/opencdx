import React from 'react';
import { View, Text, Platform , Button, ButtonText} from 'react-native';



const OrderPlacedScreen = ({navigation}) => {
    return (
        <View style={styles.container}>
            <Text>Your order has been submitted for processing.</Text>
            <Text>Your order number is: 2024CD-0528</Text>
            <Button title="Ok" style={styles.button} onPress={() => navigation.navigate('List')}>
            </Button>
        </View>
    );
};
const styles = {
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        padding: 10,
        ...Platform.select({
            web: {
                width: 500,
                margin: 'auto',
                justifyContent: 'center',
                alignItems: 'center',
            },
            default: {
                alignItems: 'center',
                justifyContent: 'center',
            }
        })
    },
};

export default OrderPlacedScreen;
