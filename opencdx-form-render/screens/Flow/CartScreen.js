import React, { useState } from 'react';
import { View, Text, StyleSheet, Platform, TextInput, Button } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { AntDesign } from '@expo/vector-icons';
import { TouchableOpacity } from 'react-native-gesture-handler';
import { MaterialIcons } from '@expo/vector-icons';
import { useNavigation } from '@react-navigation/native';
import Colors from '../../utils/colors';
import { SafeAreaView } from 'react-native-safe-area-context';


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
                <View style={styles.leftSectionHeader}>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{paddingTop: 5}}>
                        <Ionicons name="arrow-back" size={20} color="black" />
                    </TouchableOpacity>

                    {/* Test Details */}
                    <Text style={styles.title}>Cart</Text>
                </View>
                {/* Search Icon */}
                <View style={styles.rightSection}>
                   

                    {/* Cart Icon */}
                    <TouchableOpacity onPress={() => console.log('Cart')} style={{marginRight: 10}}>
                    <MaterialIcons name="favorite-border" size={20} color="black" />
                                        </TouchableOpacity>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{marginRight: 10}}>
                        <AntDesign name="search1" size={20} color="black" />
                    </TouchableOpacity>
                </View>
            </View>
          
          
                       
                  
            <View >
                <Text style={{fontWeight: 'bold', fontSize: 20, marginTop: 10, marginBottom: 10
            }}>Order Summary </Text>
             <View style={styles.testDescription}>
                <Text style={{fontWeight: 'bold', marginTop: 10, fontSize:18}}>{item.name}</Text>
                <Text style={{color: 'gray', fontSize: 16, marginTop: 5}}>{item.description}</Text>
               
            </View>
                <View style={styles.couponBox}>
            <TextInput
                    placeholder="Enter coupon code"
                    value={couponCode}
                    onChangeText={setCouponCode}
                    style={{ padding: 5, borderRadius: 5}}
                />
                <Button title="Apply" onPress={applyCoupon} style={{border: '1px solid lightgray', padding: 5, borderRadius: 5}} />
                {appliedCoupon && <Text>Applied Coupon: {appliedCoupon}</Text>}
            </View>
            <View style={styles.sectionWrapper}>
                            <Text style={{ fontSize: 18,paddingBottom: 10}}
                            >Insurance</Text>
                           
                            <TouchableOpacity  style={{marginLeft: 10, paddingTop: 2}}
                            onPress={() => navigation.navigate('Insurance')}>
                            <AntDesign name="right" size={20} color="green" />
                            </TouchableOpacity>
                        </View>
            <View style={styles.sectionWrapper}>
                            <Text style={{ fontSize: 18,paddingBottom: 10}}
                            >Payment Method</Text>
                           
                            <TouchableOpacity  style={{marginLeft: 10, paddingTop: 2}}
                            onPress={() => navigation.navigate('PaymentMethod')}>
                            <AntDesign name="right" size={20} color="green" />
                            </TouchableOpacity>
                        </View>
                        <View style={styles.sectionWrapper}>
                            <Text style={{ fontSize: 18,paddingBottom: 10}}
                            >Shipping Address</Text>
                           
                            <TouchableOpacity  style={{marginLeft: 10, paddingTop: 2}}
                            onPress={() => navigation.navigate('Address')}>
                            <AntDesign name="right" size={20} color="green" />
                            </TouchableOpacity>
                        </View>
                <View style={styles.summary}>
                    <View style={styles.summaryText}>
                        <Text style={{fontWeight: 'bold',fontSize: 18}}>Items: </Text>
                        <Text style={{fontSize: 18}}>{item.name}</Text>
                        </View>
                        <View style={styles.summaryText}>
                        <Text  style={{fontWeight: 'bold',fontSize: 18}}>Subtotal: </Text>
                        <Text style={{fontSize: 18}}>{total}</Text>
                        </View>
                        <View style={styles.summaryText}>
                        <Text  style={{fontWeight: 'bold',fontSize: 18}}>Discount: </Text>
                        <Text style={{fontSize: 18}}>$0</Text>
                        </View>
                    <View style={{borderTop: '1px solid lightgray', margin: 5}}></View>
                        <View style={styles.summaryText}>
                        <Text  style={{fontWeight: 'bold',fontSize: 18}}>Total: </Text>
                        <Text style={{fontSize: 18}}>${total}</Text>
                        </View>
                  
                </View>
            </View>

            <View style={styles.footer}>
            <SafeAreaView edges={['bottom']} style={{ backgroundColor: '#fff' }}>
              <TouchableOpacity style={styles.fullButton} onPress={() => navigation.navigate('OrderPlaced')} >
                <Text style={styles.footerText}>Proceed</Text>
                <Text style={styles.basketTotal}>${price * count}</Text>
              </TouchableOpacity>
          </SafeAreaView>
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
                width: 500,
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
    leftSectionHeader: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'flex-start',
    },
    title: {
        marginLeft: 10,
        paddingTop: 5,
        fontSize: 18,
        fontWeight: 'bold',
    },
    subText:
    {
        fontSize: 16,
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
    sectionWrapper: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#f0f0f0',
        paddingVertical: 5,
        paddingHorizontal: 10,


    },
    leftSection: {
        padding: 10,
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'flex-start',


    },
    rightSection: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        

      
    },
    footer: {
        position: 'absolute',
        backgroundColor: '#fff',
        bottom: 0,
        left: 0,
        width: '100%',
        padding: 10,
        elevation: 10,
        shadowColor: '#000',
        shadowOffset: { width: 0, height: -10 },
        shadowOpacity: 0.1,
        shadowRadius: 10,
      },
      fullButton: {
        backgroundColor: 'rgb(0, 102, 255)',
        paddingHorizontal: 16,
        borderRadius: 8,
        alignItems: 'center',
        flexDirection: 'row',
        flex: 1,
        justifyContent: 'space-between',
        height: 50,
        padding: 8,

      },
      footerText: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 16,
      },
      basket: {
        color: '#fff',
        backgroundColor: 'rgb(0, 102, 255)',
        fontWeight: 'bold',
        padding: 8,
        borderRadius: 2,
      },
      basketTotal: {
        color: '#fff',
        fontWeight: 'bold',
        fontSize: 16,
      },
});

export default CartScreen;
