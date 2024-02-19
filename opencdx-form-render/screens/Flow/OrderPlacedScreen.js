import React from 'react';
import { View, Text, Platform } from 'react-native';


const OrderPlacedScreen = () => {
    return (
        <View style={styles.container}>
            <Text>Your order has been submitted for processing.</Text>
            <Text>Your order number is: 2024CD-0528</Text>
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
                maxWidth: 500,
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
