import React, { useState } from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import axios from 'axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
    Image,
} from '@gluestack-ui/themed';
import { TextInput, Switch, Button, Text } from 'react-native-paper';


const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('admin@opencdx.org');
    const [password, setPassword] = useState('password');

    const [isSwitchOn, setIsSwitchOn] = React.useState(false);

    const onToggleSwitch = () => setIsSwitchOn(!isSwitchOn);

    const handleLogin = async () => {
        try {
            const response = await axios.post('https://localhost:8080/iam/user/login', { userName: username, password: password });
            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('List');
        } catch (error) {
            alert(error);
        }
    };

    return (
        <SafeAreaView style={styles.container}>
            <View style={styles.body}>
                <Image
                    size="md"
                    resizeMode="contain"
                    alt="OpenCDX logo"
                    style={styles.image}
                    source={require('../assets/opencdx.png')}
                />
                <TextInput
                    label="Email"
                    mode='outlined'
                    value={username}
                    style={styles.input}
                    onChangeText={username => setUsername(username)}
                />
                <TextInput
                    label="Password"
                    mode='outlined'
                    style={styles.input}
                    onChangeText={password => setPassword(password)}
                    value={password}
                    secureTextEntry
                    right={<TextInput.Icon icon="eye" />}
                />

                <View style={styles.switchWrapper}>
                    <View style={styles.switch}>
                        <Switch value={isSwitchOn} onValueChange={onToggleSwitch} /><Text style={{ marginLeft: 10, marginTop: 10 }}
                        >Remember Me</Text></View>

                    <Text style={styles.forget}>
                        Forget Password </Text>
                </View>
            </View>
            <View style={styles.footer}>
                <Button mode="contained-tonal" width='100%' title="Sign In" style={styles.button} onPress={handleLogin}>
                    Sign In
                </Button>
                <View style={styles.center}>
                    <Text style={styles.centerText}>Don't have an account?</Text>
                    <Text style={styles.signup}>
                        Sign Up
                    </Text>
                </View>
            </View>
        </SafeAreaView>
    );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        shadowColor: "#000",
        backgroundColor: '#fff',
        ...Platform.select({
            web: {
                maxWidth: 500,
                margin: 'auto',
                justifyContent: 'center',
            },
            default: {
                justifyContent: 'space-between',
            }
        })
    },
    switchWrapper: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'space-between',
    },
    switch: {
        flexDirection: 'row',
    },
    center: {
        flexDirection: 'row',
        alignItems: 'center',
        justifyContent: 'center',
        paddingTop: 24,
    },
    centerText: {
        marginRight: 5,
    },
    input: {
        marginBottom: 24,
    },
    button: {
        marginBottom: 10,
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 68,
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            }
        })
    },
    image: {
        marginBottom: 48,
        justifyContent: 'center',
        width: 500,
    },
    forget: {
        alignItems: 'flex-end',
        marginBottom: 10,
        marginTop: 10,
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "flex-end",
        color: '#0066FF',
        fontWeight: 500,
    },
    signup: {
        textAlign: 'right',
        flexDirection: "row",
        justifyContent: "center",
        color: '#0066FF',
        fontWeight: 500,
    },
    body: {
        ...Platform.select({
            web: {
                padding: 24,
            },
            default: {
                flex: 1,
                justifyContent: 'center',
                padding: 24,
            }
        })
    },
    footer: {
        alignContent: 'center',
        justifyContent: 'center',
        alignItems: 'center',
        alignItems: 'center',
        justifyContent: 'center',
        borderRadius: 4,
        paddingTop: 24,
    },
});

export default LoginScreen;