import React, { useState } from 'react';
import { View, StyleSheet, Platform, SafeAreaView } from 'react-native';
import axios from '../utils/axios';
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
    Button, Input, ButtonText, InputField, Image, Link, LinkText, Text,
    InputSlot,
    InputIcon,
    EyeIcon,
    EyeOffIcon,
    Switch,
} from '@gluestack-ui/themed';

const LoginScreen = ({ navigation }) => {
    const [username, setUsername] = useState('admin@opencdx.org');
    const [password, setPassword] = useState('password');
    const [showPassword, setShowPassword] = useState(false)
    const handleState = () => {
        setShowPassword((showState) => {
            return !showState
        })
    }
    const handleLogin = async () => {
        try {
            const response = await axios.post('/iam/user/login', { userName: username, password: password });
            await AsyncStorage.setItem('jwtToken', response.data.token);
            navigation.navigate('List');
        } catch (error) {
            alert('Invalid credentials');
        }
    };
    const [rememberMe, setRememberMe] = useState(false);
    const toggleRememberMe = (value) => {
        setRememberMe(value);
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
                <Input
                    onChangeText={setUsername}
                    style={styles.input}
                    variant="outlined"
                    size="md"
                >
                    <InputField placeholder="Email  " defaultValue={username} />
                </Input>
                <Input
                    onChangeText={setPassword}
                    style={styles.input}
                    variant="outlined"
                    size="md"
                >
                    <InputField type={showPassword ? "text" : "password"} placeholder="Password" defaultValue={password} />
                    <InputSlot pr="$3" onPress={handleState}>
                        <InputIcon
                            as={showPassword ? EyeIcon : EyeOffIcon}
                            color="$darkBlue500"
                        />
                    </InputSlot>
                </Input>
                <View style={styles.switchWrapper}>
                    <View style={styles.switch}>
                    <Switch
                        value={rememberMe}
                        onValueChange={(value) => toggleRememberMe(value)}

                    /><Text style={{ marginLeft: 10 }}
                    >Remember Me</Text></View>
                     
                    <Text style={styles.forget}>
                    Forget Password </Text>
                </View>
            </View>
            <View style={styles.footer}>
                <Button title="Sign In" onPress={handleLogin} style={styles.button}>
                    <ButtonText style={styles.buttonText}>Sign In</ButtonText>
                </Button>
                <View style={styles.center}>
                    <Text style={styles.centerText}>Don't have an account?</Text>
                    <Text  style={styles.signup}>
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
        paddingTop:24,
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
        backgroundColor: '#0066FF',
        ...Platform.select({
            web: {
                width: '90%',
            },
            default: {
                width: '90%',
            }
        })
    },
    buttonText: {
        color: 'white',
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