import React, { useState } from 'react';
import { View, Text, StyleSheet, Platform, TextInput, Button } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { AntDesign } from '@expo/vector-icons';
import { Feather } from '@expo/vector-icons';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { MaterialIcons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';



const CartScreen = ({ route }) => {
    const { total, item, count } = route.params;
    const [couponCode, setCouponCode] = useState('');
    const [appliedCoupon, setAppliedCoupon] = useState('');
    const navigation = useNavigation();
    const price = item.price;


    const availableCoupons = ['COUPON1', 'COUPON2', 'COUPON3']; // List of available coupons in the drawer

    const applyCoupon = () => {
        if (availableCoupons.includes(couponCode)) {
            setAppliedCoupon(couponCode);
            console.log('Coupon code applied:', couponCode);
        } else {
            console.log('Invalid coupon code:', couponCode);
        }
    };

    return (
        <View style={styles.container}>
            <View style={styles.header}>
                {/* Back Arrow */}
                <View style={styles.leftSection}>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{marginLeft: 10,paddingTop: 5}}>
                        <Ionicons name="arrow-back" size={16} color="black" />
                    </TouchableOpacity>

                    {/* Test Details */}
                    <Text style={styles.title}>Cart</Text>
                </View>
                {/* Search Icon */}
                <View style={styles.rightSection}>
                   

                    {/* Cart Icon */}
                    <TouchableOpacity onPress={() => console.log('Cart')} style={{marginRight: 10}}>
                    <MaterialIcons name="favorite-border" size={16} color="black" />
                                        </TouchableOpacity>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{marginRight: 10}}>
                        <AntDesign name="search1" size={16} color="black" />
                    </TouchableOpacity>
                </View>
            </View>
           <View style={styles.testDescription}>
                <Text style={{fontWeight: 'bold', marginTop: 10}}>{item.name}</Text>
                <Text style={{color: 'gray', fontSize: 12, marginTop: 5}}>{item.description}</Text>
               
            </View>

            <View style={styles.couponBox}>
                <Text>Apply  Coupon</Text>
                <Text>View Offers</Text>
               

            </View>
            <View style={styles.couponBox}>
            <TextInput
                    placeholder="Enter coupon code"
                    value={couponCode}
                    onChangeText={setCouponCode}
                />
                <Button title="Apply" onPress={applyCoupon} />
                {appliedCoupon && <Text>Applied Coupon: {appliedCoupon}</Text>}
            </View>
            
                    <View >
                        <Text>Shipping Address</Text>
                        <Text> Insurance Details</Text>
                        <Text>Payment Method</Text>

                       

                    </View>
            <View >
                <Text>Order Summary </Text>
                <View style={styles.summary}>
                    <View style={styles.summaryText}>
                        <Text>Items: </Text>
                        <Text>{item.name}</Text>
                        </View>
                        <View style={styles.summaryText}>
                        <Text>Subtotal: </Text>
                        <Text>{total}</Text>
                        </View>
                        <View style={styles.summaryText}>
                        <Text>Discount: </Text>
                        <Text>$0</Text>
                        </View>
                    <View style={{borderTop: '1px solid lightgray', margin: 5}}></View>
                        <View style={styles.summaryText}>
                        <Text>Total: </Text>
                        <Text>${total}</Text>
                        </View>
                  
                </View>
            </View>
            <View style={styles.stickyBottom}>
                <View style={styles.leftSection}>

                        {/* Test Details */}
                        <Text style={styles.subText}>$ {total} </Text>
                    </View>
                <TouchableOpacity onPress={() => navigation.navigate('OrderPlaced')} style={{backgroundColor: 'green', padding: 10, borderRadius: 10, marginLeft:5}}>
                    <Text style={{color: '#fff', fontWeight: 'bold'}}>Proceed</Text>
                </TouchableOpacity>
                </View>
        </View>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        padding: 10,
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
            },
            default: {

            }
        })
    },
    testDescription: {
        padding:5,
        border:'1px solid lightgray',

        borderRadius: 5,
        paddingBottom: 10,
    },
    couponBox: {
        padding: 5,
        border:'1px solid lightgray',
        borderRadius: 5,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        marginBottom: 10,

    },
    header: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 16,
        paddingTop: Platform.OS === 'ios' ? 40 : 20,
        paddingBottom: 16,
        backgroundColor: '#fff',
        borderBottomWidth: 1,
        borderBottomColor: '#E5E5E5',
        marginBottom: 16,
    },
    leftSection: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    title: {
        marginLeft: 10,
        fontSize: 16,
        fontWeight: 'bold',
    },
    subText:
    {
        fontSize: 12,
        color: 'gray',
        marginLeft: 10,
    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    summary: {
        padding: 10,
        border:'1px solid lightgray',
        borderRadius: 5,
        justifyContent: 'space-between',
    },
    summaryText: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        padding: 5,
    },
    stickyBottom: {
        position: 'absolute',
        bottom: 10,
        left: 0,
        right: 0,
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        paddingHorizontal: 16,
        paddingVertical: 12,
        backgroundColor: '#fff',
    },
});

export default CartScreen;
