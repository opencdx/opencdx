import React, {  useState } from 'react';
import { View, Text, TouchableOpacity, Image } from 'react-native';
import { Ionicons } from '@expo/vector-icons';
import { AntDesign } from '@expo/vector-icons';
import { Feather } from '@expo/vector-icons';
import { Platform } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import imageS from '../../assets/flow.jpeg';

import Colors from '../../utils/colors';
import { SafeAreaView } from 'react-native-safe-area-context';





const TestDetailsScreen = ({ route }) => {
    const { items, total } = route.params;

    const navigation = useNavigation();
    const [price, setPrice] = useState(route.params.test.price);
    const [count, setCount] = useState(1);

    const incrementCount = () => {
        setCount(count + 1);
    };

    const decrementCount = () => {
        if (count > 1) {
            setCount(count - 1);
        }
    };

    return (
        <View style={styles.container}>
            {/* Header */}
            <View style={styles.header}>
                {/* Back Arrow */}
                <View style={styles.leftSection}>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{ paddingTop: 5 }}>
                        <Ionicons name="arrow-back" size={16} color="black" />
                    </TouchableOpacity>

                    {/* Test Details */}
                    <Text style={styles.title}>Test Details</Text>
                </View>
                {/* Search Icon */}
                <View style={styles.rightSection}>
                    <TouchableOpacity onPress={() => navigation.goBack()} style={{ marginRight: 10 }}>
                        <AntDesign name="search1" size={16} color="black" />
                    </TouchableOpacity>

                    {/* Cart Icon */}
                    <TouchableOpacity onPress={() => console.log('Cart')} style={{ marginRight: 10 }}>
                        <Feather name="shopping-cart" size={16} color="black" />
                    </TouchableOpacity>
                </View>
            </View>
            {/* Test Details */}
            <View style={styles.testDetails}>
                <Image
                    source={imageS}
                    style={{ width: 230, height: 200 }}
                />

            </View>
            <Text style={{ fontWeight: 'bold', marginTop: 10, fontSize: 20 }}>Test Kit Contents</Text>
            <View style={{ borderBottomWidth: 1, borderBottomColor: '#E5E5E5', marginTop: 10 }}></View>

            <View style={styles.testDescription}>
                <Text style={styles.checklist} > <Feather name="check" size={16} color="green" />  1  Test Cassette</Text>
                <Text style={styles.checklist}> <Feather name="check" size={16} color="green" />  1  Disposable Swab</Text>
                <Text style={styles.checklist}> <Feather name="check" size={16} color="green" />  1  Extraction Buffer Tube</Text>
                <Text style={styles.checklist}> <Feather name="check" size={16} color="green" />  1  Package Insert</Text>
                <Text style={styles.checklist}> <Feather name="check" size={16} color="green" />  Quick Reference Instructions</Text>
            </View>
            <Text style={{ fontWeight: 'bold', marginTop: 10, fontSize: 20 }}>Product Details</Text>
            <View style={{ borderBottomWidth: 1, borderBottomColor: '#E5E5E5', marginTop: 10 }}></View>


            <View style={styles.testDescription}>
                <Text style={{ fontWeight: 'bold', fontSize: 18, marginTop: 10 }}>{route.params.test.name}</Text>
                <Text style={{ color: 'gray', fontSize: 18, marginTop: 5 }}>{route.params.test.description}</Text>
                <View style={{ borderBottomWidth: 1, borderBottomColor: '#E5E5E5', marginTop: 10 }}></View>
                <View style={{ flexDirection: 'row', justifyContent: 'space-between', marginTop: 10 }}>
                    <View style={styles.card}>
                        <Text style={{ fontWeight: 'bold', marginTop: 5, fontSize: 16 }}>FASTING</Text>
                        <Text style={{ color: 'gray', fontSize: 16, marginTop: 5, alignItems: 'center', padding: 5, borderRadius: 5, justifyContent: 'center', alignItems: 'center' }}>No</Text>
                    </View>
                    <View style={styles.card}>
                        <Text style={{ fontWeight: 'bold', marginTop: 5, fontSize: 16 }}>SAMPLE</Text>
                        <Text style={{ color: 'gray', fontSize: 16, marginTop: 5, alignItems: 'center', padding: 5, borderRadius: 5, justifyContent: 'center', alignItems: 'center' }}>Blood</Text>
                    </View>
                    <View style={styles.countWrapper}>
                        <TouchableOpacity onPress={decrementCount} style={styles.countButton}>
                            <Text style={styles.countButtonText}>-</Text>
                        </TouchableOpacity>
                        <Text style={styles.count}>{count}</Text>
                        <TouchableOpacity onPress={incrementCount} style={styles.countButton}>
                            <Text style={styles.countButtonText}>+</Text>
                        </TouchableOpacity>
                        <Text style={styles.price}>
                            Price: ${price * count}
                        </Text>
                    </View>
                </View>

            </View>

            {/* <View style={styles.stickyBottom}>
                <View style={styles.leftSection}>
                    <Feather name="shopping-cart" size={16} color="black" />

                    <Text style={styles.subText}>{count} items in the Cart</Text>
                </View>
                <TouchableOpacity onPress={() => navigation.navigate('Cart', { total: price * count, item: route.params.test, count, count })} style={{ backgroundColor: 'green', padding: 10, borderRadius: 10, marginLeft: 5 }}>
                    <Text style={{ color: '#fff', fontWeight: 'bold' }}>Proceed</Text>
                </TouchableOpacity>
            </View> */}

            <View style={styles.footer}>
            <SafeAreaView edges={['bottom']} style={{ backgroundColor: '#fff' }}>
              <TouchableOpacity style={styles.fullButton} onPress={() => navigation.navigate('Cart', { total: price * count, item: route.params.test, count, count })} >
                <Text style={styles.basket}>{count}</Text>
                <Text style={styles.footerText}>View Basket</Text>
                <Text style={styles.basketTotal}>${price * count}</Text>
              </TouchableOpacity>
          </SafeAreaView>
        </View>
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

            },
            default: {
            }
        })
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
    },
    leftSection: {
        flexDirection: 'row',
        alignItems: 'center',
    },
    title: {
        marginLeft: 10,
        fontSize: 20,
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
    testDetails: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',


    },
    checklist: {
        fontSize: 18,
        color: 'gray',
    },
    testDescription: {
        padding: 5,
        border: '1px solid lightgray',

        borderRadius: 5,
    },
    card: {
        marginRight: 16,
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
    countWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        borderBottomWidth: 1,
        borderBottomColor: '#E5E5E5',
        justifyContent: 'flex-end',
        paddingHorizontal: 16,
        paddingVertical: 12,
        backgroundColor: '#fff',
    },

    countButton: {
        width: 32,
        height: 32,
        borderRadius: 16,
        backgroundColor: '#E5E5E5',
        justifyContent: 'center',
        alignItems: 'center',
        marginLeft: 10,
        marginRight: 10,
    },
    countButtonText: {
        fontSize: 16,
        fontWeight: 'bold',
    },
    count: {
        fontSize: 16,
        fontWeight: 'bold',
    },
    price: {
        fontSize: 16,
        fontWeight: 'bold',
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
        paddingTop: 20,
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
};

export default TestDetailsScreen;
