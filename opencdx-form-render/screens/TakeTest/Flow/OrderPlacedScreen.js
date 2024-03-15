import React from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import AnimatedCard from '../../../components/AnimatedCard';


const OrderPlacedScreen = ({ navigation }) => {

    return (
        <View style={styles.container}>
            <SafeAreaView style={styles.body}>
                <AnimatedCard navigation={navigation} link='List' src={require('../../../assets/success.json')} title='Order Placed' subtitle='Your order has been submitted for processing.' />
            </SafeAreaView>
        </View>
    );
};
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        shadowColor: "#000",
        margin: 'auto',
        ...(Platform.select({
            web: {
                minWidth: 500,
            },
            default: {
                margin: 20,
                shadowColor: "#000",
            },
        })),
    },

    surface: {
        margin: 5,
        elevation: 2,
        justifyContent: 'space-between',
        alignItems: 'center',

    },
    leftSection: {

    },

    animationContainer: {
        justifyContent: 'center',
        alignItems: 'center',


    },
    buttonContainer: {
        paddingTop: 20,
    },
    button: {

        marginTop: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,

    },

});

export default OrderPlacedScreen;
